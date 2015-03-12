package com.jiangw.widgets;

import com.jiangw.base.AppConfig;
import com.jiangw.db.NoteConstant;
import com.jiangw.interfaces.clockCallBack;
import com.jiangw.note.R;
import com.jiangw.note.items.ClockDialogActivity;
import com.jiangw.utils.TimeUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ClockPopupWindow // implements clockCallBack
{
	private static final String TAG = "ClockPopupWindow";
	private Context context;
	private View view;
	private PopupWindow mPopupWindow;
	private LayoutInflater mInflater;
	private ContentResolver cr;
	private static ClockPopupWindow instance;
	private TextView clockTime;

	public ClockPopupWindow(Context context, View view)
	{
		this.context = context;
		this.view = view;
		mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cr = this.context.getContentResolver();
		// 内部
	}

	private ClockPopupWindow()
	{
		new ClockPopupWindow(context, view);
	}

	public static ClockPopupWindow getInstance()
	{
		if (instance == null)
		{
			instance = new ClockPopupWindow();
		}
		return instance;
	}

	public void showPopupWindow(final long id, final View childView)
	{
		if (mPopupWindow == null)
		{
			View mView = mInflater.inflate(R.layout.set_clock, null);
			final LinearLayout mClock = (LinearLayout) mView.findViewById(R.id.ll_clock);
			final TextView clockState = (TextView) mView.findViewById(R.id.tv_clock_state);
			clockTime = (TextView) mView.findViewById(R.id.tv_clock_time);
			final SwitchButton switchButton = (SwitchButton) mView.findViewById(R.id.cb_clock_switch);
			// 查询数据库得到时间和标识state

			String projection[] = new String[] { NoteConstant.Notes.NOTE_ALERT_CLOCK_COLUMN, NoteConstant.Notes.NOTE_CLOCK_STATE_COLUMN };
			Cursor cursor = cr.query(NoteConstant.Notes.TAB_NOTES_URI, projection, NoteConstant.Notes.NOTE_TAB_ID + " = ?", new String[] { "" + id }, null);
			long time = 0;
			int state = 0;
			if (cursor != null)
			{
				if (cursor.moveToFirst())
				{
					time = cursor.getLong(cursor.getColumnIndex(NoteConstant.Notes.NOTE_ALERT_CLOCK_COLUMN));
					state = cursor.getInt(cursor.getColumnIndex(NoteConstant.Notes.NOTE_CLOCK_STATE_COLUMN));
				}
			}
			// 初始状态
			if (state == 0)
			{
				switchButton.setChecked(false);
				clockState.setText("闹钟关闭");
			} else
			{
				switchButton.setChecked(true);
				clockState.setText("闹钟打开");
			}
			// 初始时间
			if (time == 0)
			{
				// clockTime.setText(TimeUtils.getSysYearMonthDay());
			} else
			{
				System.out.println("ClockPopupWindow time="+time);
				clockTime.setText(TimeUtils.getYearMonthDay(time));
			}
			mClock.setOnClickListener(new android.view.View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (switchButton.isChecked())
					{
						// 弹出时间选择器,选择好时间点击确定，startActivityForResult
						if (AppConfig.app.getActivity() != null)
							AppConfig.app.getActivity().finish();
						Intent intent = new Intent(context, ClockDialogActivity.class);
						intent.putExtra(AppConfig.CURRENT_CLOCK_TIME, clockTime.getText().toString());
						intent.putExtra(AppConfig.UPDATE_CLOCK_TIME_ID, id);
						((Activity) context).startActivityForResult(intent, 0);
//						context.startActivity(intent);
					}
				}
			});

			switchButton.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					// TODO Auto-generated method stub
					if (isChecked)
					{
						clockState.setText("闹钟打开");
						// 更新数据库
						ContentValues values = new ContentValues();
						values.put(NoteConstant.Notes.NOTE_CLOCK_STATE_COLUMN, 1);
						cr.update(NoteConstant.Notes.TAB_NOTES_URI, values, NoteConstant.Notes.NOTE_TAB_ID + " = ?", new String[] { "" + id });
						// 再弹pop显示时间选择器
						Intent intent = new Intent(context, ClockDialogActivity.class);
						intent.putExtra(AppConfig.UPDATE_CLOCK_TIME_ID, id);
						((Activity) context).startActivityForResult(intent, 0);
					} else
					{
						clockState.setText("闹钟关闭");
						// 更新数据库
						ContentValues values = new ContentValues();
						values.put(NoteConstant.Notes.NOTE_CLOCK_STATE_COLUMN, 0);
						cr.update(NoteConstant.Notes.TAB_NOTES_URI, values, NoteConstant.Notes.NOTE_TAB_ID + " = ?", new String[] { "" + id });
						// 关闭pop时间选择器
						if (AppConfig.app.getActivity() != null)
							AppConfig.app.getActivity().finish();
						updateTime("");
					}
				}
			});

			mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			mPopupWindow.setFocusable(true);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
			mPopupWindow.setAnimationStyle(R.style.clockAnimation);
			mPopupWindow.showAtLocation(view, Gravity.RIGHT | Gravity.TOP, 0, 200);
		}
	}

	public boolean isShow()
	{
		return mPopupWindow != null && mPopupWindow.isShowing();
	}

	public void closePop()
	{
		if (isShow())
			mPopupWindow.dismiss();
	}

	// public PopupWindow getMpop(){
	// return mPopupWindow;
	// }
	public void updateTime(String time)
	{
		clockTime.setText(time);
	}
}
