package com.jiangw.note.items;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jiangw.base.AppConfig;
import com.jiangw.base.BaseActivity;
import com.jiangw.db.NoteConstant;
import com.jiangw.note.R;
import com.jiangw.note.schedule.AlarmReceiver;
import com.jiangw.utils.ResourceParser;
import com.jiangw.utils.ShareprefenceUtils;
import com.jiangw.utils.TimeUtils;
import com.jiangw.utils.ToastUitls;
import com.jiangw.widgets.ClockPopupWindow;
import com.jiangw.widgets.ColorSelectorPopWindow;
import com.jiangw.widgets.wheelview.WheelMain;

public class EditActivity extends BaseActivity
{
	private static final String TAG = "EditActivity";
	private RelativeLayout title_bg;
	private TextView title_date;
	private TextView title_clock;
	private TextView title_selector;
	private EditText et_content;
	private ColorSelectorPopWindow mPop;
	private ClockPopupWindow mClockPopupWindow;
	/** noteId=-1表示新建,noteId!=-1表示修改数据库中_id=noteId的数据 */
	private long noteId;
	/** 初始文本内容 */
	private String contentSource;
	private static final int SHOW_TIMEPICKER = 1;
	private WheelMain wheelMain;
	private Dialog timeSelectedDiolog;
	private boolean isSet = true;
	private AlarmManager alarmManager;
	private PendingIntent pendingIntent;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case RESULT_OK:// -1
				title_bg.setBackgroundResource(ResourceParser.NoteBgResource.getNoteTitleResource(ShareprefenceUtils.getInstance(EditActivity.this).getNoteBgSelectedId()));
				et_content.setBackgroundResource(ResourceParser.NoteBgResource.getNoteBgResource(ShareprefenceUtils.getInstance(EditActivity.this).getNoteBgSelectedId()));
				break;
			case SHOW_TIMEPICKER:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		initView();
		initListener();
	}

	private void initView()
	{
		noteId = getIntent().getLongExtra(AppConfig.NEW_OR_UPDATE_ID, -1);
		title_bg = (RelativeLayout) findViewById(R.id.title_bg1);
		title_date = (TextView) findViewById(R.id.title_time);
		title_clock = (TextView) findViewById(R.id.title_clock);
		title_selector = (TextView) findViewById(R.id.title_selected_bg);
		et_content = (EditText) findViewById(R.id.et_main);

		// 如果是新建或者打开修改这用当前时间，如果是打开未修改则用原时间
		if (-1 == noteId)
		{
			title_date.setText(TimeUtils.getSysYearMonthDay());
		} else
		{
			Cursor cursor = getContentResolver().query(Uri.withAppendedPath(NoteConstant.Notes.TAB_NOTES_URI, noteId + ""), null, null, null, null);
			if (cursor != null)
			{
				while (cursor.moveToNext())
				{
					String time = cursor.getString(cursor.getColumnIndex(NoteConstant.Notes.NOTE_DATE_COLUMN));
					int bgId = cursor.getInt(cursor.getColumnIndex(NoteConstant.Notes.NOTE_BGCOLOR_COLUMN));
					String content = cursor.getString(cursor.getColumnIndex(NoteConstant.Notes.NOTE_CONTENT_COLUMN));
					ShareprefenceUtils.getInstance(EditActivity.this).setNoteBgSelectedId(bgId);
					et_content.setText(content);
					if (!TextUtils.isEmpty(time))
					{
						title_date.setText(TimeUtils.getYearMonthDay(Long.parseLong(time)));
					} else
					{
						title_date.setText(TimeUtils.getSysYearMonthDay());
					}
				}
			}
		}
		title_bg.setBackgroundResource(ResourceParser.NoteBgResource.getNoteTitleResource(ShareprefenceUtils.getInstance(this).getNoteBgSelectedId()));
		et_content.setBackgroundResource(ResourceParser.NoteBgResource.getNoteBgResource(ShareprefenceUtils.getInstance(this).getNoteBgSelectedId()));
		contentSource = et_content.getText().toString();

	}

	private Dialog initDialog()
	{
		if (timeSelectedDiolog == null)
		{
			// 时间选择器
			long alertTime = isSet ? queryAlertClock(noteId) : 0;
			timeSelectedDiolog = new Dialog(EditActivity.this, 0);
			timeSelectedDiolog.setTitle("设置闹钟时间");
			timeSelectedDiolog.setContentView(R.layout.wheel_select_time_dialog);
			View timePicker = timeSelectedDiolog.findViewById(R.id.timePicker1);
			wheelMain = new WheelMain(timePicker);
			wheelMain.initDateTimePicker(alertTime);
			Button btnSure = (Button) timeSelectedDiolog.findViewById(R.id.buttonsure);
			Button btnCancle = (Button) timeSelectedDiolog.findViewById(R.id.buttoncancle);
			btnCancle.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					ToastUitls.tL(EditActivity.this, "取消");
					timeSelectedDiolog.dismiss();
				}
			});
			btnSure.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					// 定闹钟先保存笔记
					isSave();
					Intent intent = new Intent(EditActivity.this, AlarmReceiver.class);
					intent.setData(ContentUris.withAppendedId(NoteConstant.Notes.TAB_NOTES_URI, noteId));
					pendingIntent = PendingIntent.getBroadcast(EditActivity.this, 0, intent, 0);
					alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
					alarmManager.set(AlarmManager.RTC_WAKEUP, TimeUtils.getLongYearMonthDay(wheelMain.getTime()), pendingIntent);
					ContentValues values = new ContentValues();
					values.put(NoteConstant.Notes.NOTE_ALERT_CLOCK_COLUMN, TimeUtils.getLongYearMonthDay(wheelMain.getTime()));
					ContentResolver cr = getContentResolver();
					int uid = cr.update(NoteConstant.Notes.TAB_NOTES_URI, values, NoteConstant.Notes.NOTE_TAB_ID + " = " + noteId, null);
					// System.out.println("alert="+uid);
					timeSelectedDiolog.dismiss();
					isSet = true;
				}
			});
		}

		return timeSelectedDiolog;
	}

	private void initListener()
	{
		/** 弹出背景颜色设置PopupWindow */
		title_selector.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				mPop = new ColorSelectorPopWindow(EditActivity.this, v);
				if (mPop.isShow())
				{
					mPop.closePop();
				} else
				{
					mPop.showPop(ShareprefenceUtils.getInstance(EditActivity.this).getNoteBgSelectedId(), mHandler);
				}
			}
		});
		/** 弹出闹钟设置对话框 */
		title_clock.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// initDialog().show();
				// 弹出是否开启闹钟pop
				// 先保存，否则不能设置闹钟
				mClockPopupWindow = new ClockPopupWindow(EditActivity.this, v);
				if (mClockPopupWindow.isShow())
				{
					mClockPopupWindow.closePop();
				} else
				{
					// 当前便签的id值
					mClockPopupWindow.showPopupWindow(noteId, findViewById(R.layout.activity_edit));
				}
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		isSave();
		super.onBackPressed();
	}

	private void isSave()
	{
		String content = et_content.getText().toString();
		String time = title_date.getText().toString();
		int bgId = ShareprefenceUtils.getInstance(EditActivity.this).getNoteBgSelectedId();
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(NoteConstant.Notes.NOTE_CONTENT_COLUMN, content);
		if (contentSource.equals(content))
		{
			values.put(NoteConstant.Notes.NOTE_DATE_COLUMN, TimeUtils.getLongYearMonthDay(time));
		} else
		{
			values.put(NoteConstant.Notes.NOTE_DATE_COLUMN, System.currentTimeMillis());
		}
		values.put(NoteConstant.Notes.NOTE_BGCOLOR_COLUMN, bgId);
		if (-1 == noteId && !TextUtils.isEmpty(content))// 新建
		{
			Uri uri = cr.insert(NoteConstant.Notes.TAB_NOTES_URI, values);
			if (uri != null)
			{
				noteId = ContentUris.parseId(uri);
			}
		} else if (!TextUtils.isEmpty(content))
		{// 更新
			int uid = cr.update(NoteConstant.Notes.TAB_NOTES_URI, values, NoteConstant.Notes.NOTE_TAB_ID + " = " + noteId, null);
		}
	}

	/** 查询_id=noteId的闹钟时间 */
	private long queryAlertClock(long id)
	{
		long time = 0;
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr
				.query(NoteConstant.Notes.TAB_NOTES_URI, new String[] { NoteConstant.Notes.NOTE_ALERT_CLOCK_COLUMN }, NoteConstant.Notes.NOTE_TAB_ID + " =? ", new String[] { "" + id }, null);
		if (cursor != null)
		{
			if (cursor.moveToFirst())
			{
				time = cursor.getLong(cursor.getColumnIndex(NoteConstant.Notes.NOTE_ALERT_CLOCK_COLUMN));
			}
		}
		return time;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == 200)
		{
			String timeStr = data.getStringExtra(AppConfig.RETURN_CLOCK_TIME);
			long id = data.getLongExtra(AppConfig.UPDATE_CLOCK_TIME_ID, 0);
			mClockPopupWindow.updateTime(timeStr);
			ContentResolver cr = getContentResolver();
			ContentValues values = new ContentValues();
			
			values.put(NoteConstant.Notes.NOTE_ALERT_CLOCK_COLUMN, TimeUtils.getLongYearMonthDayTillMins(timeStr));
			int u=cr.update(NoteConstant.Notes.TAB_NOTES_URI, values, NoteConstant.Notes.NOTE_TAB_ID + " = ?", new String[] { "" + id });
			Log.d(TAG, "u="+u+" timeStr="+timeStr+" time="+TimeUtils.getLongYearMonthDayTillMins(timeStr));
		}
	}
}
