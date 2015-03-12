package com.jiangw.widgets;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.jiangw.interfaces.onPopClickCallBack;
import com.jiangw.note.R;
import com.jiangw.utils.Logs;
import com.jiangw.utils.ResourceParser;
import com.jiangw.utils.ShareprefenceUtils;

public class ColorSelectorPopWindow
{
	private static final String TAG = "PopWindow";
	private Context mContext;
	private View mView;
	private PopupWindow mPopupWindow;
	private LayoutInflater mInflater;
	private onPopClickCallBack mListener;

	public ColorSelectorPopWindow(Context context, View view)
	{
		mContext = context;
		mView = view;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@SuppressWarnings("deprecation")
	public void showPop(int showId, final Handler mHandler)
	{
		if (mPopupWindow == null)
		{
			View menuView = mInflater.inflate(R.layout.popwindow, null);
			final ImageView iv0 = (ImageView) menuView.findViewById(R.id.pop_select_0);
			final ImageView iv1 = (ImageView) menuView.findViewById(R.id.pop_select_1);
			final ImageView iv2 = (ImageView) menuView.findViewById(R.id.pop_select_2);
			final ImageView iv3 = (ImageView) menuView.findViewById(R.id.pop_select_3);
			final ImageView iv4 = (ImageView) menuView.findViewById(R.id.pop_select_4);
			switch (showId)
			{
			case ResourceParser.YELLOW:
				iv0.setVisibility(View.VISIBLE);
				break;
			case ResourceParser.BLUE:
				iv1.setVisibility(View.VISIBLE);
				break;
			case ResourceParser.WHITE:
				iv2.setVisibility(View.VISIBLE);
				break;
			case ResourceParser.GREEN:
				iv3.setVisibility(View.VISIBLE);
				break;
			case ResourceParser.RED:
				iv4.setVisibility(View.VISIBLE);
				break;
			}
			mPopupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			menuView.setOnTouchListener(new OnTouchListener()
			{

				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					// TODO Auto-generated method stub
					switch (event.getAction())
					{
					case MotionEvent.ACTION_DOWN:
						// pop总宽度和高度
						int x = v.getMeasuredWidth();
						// int y = v.getMeasuredHeight();
						Logs.i(TAG, "x=" + x);
						// pop被点击时的x和y
						float xTouch = event.getX();
						// float yTouch = event.getY();
						Logs.i(TAG, "xTouch=" + xTouch);
						if (xTouch <= x / 5)
						{
							iv0.setVisibility(View.VISIBLE);
							iv1.setVisibility(View.INVISIBLE);
							iv2.setVisibility(View.INVISIBLE);
							iv3.setVisibility(View.INVISIBLE);
							iv4.setVisibility(View.INVISIBLE);
							ShareprefenceUtils.getInstance(mContext).setNoteBgSelectedId(ResourceParser.YELLOW);
						} else if (x / 5 < xTouch && xTouch <= x * 2 / 5)
						{
							iv0.setVisibility(View.INVISIBLE);
							iv1.setVisibility(View.VISIBLE);
							iv2.setVisibility(View.INVISIBLE);
							iv3.setVisibility(View.INVISIBLE);
							iv4.setVisibility(View.INVISIBLE);
							ShareprefenceUtils.getInstance(mContext).setNoteBgSelectedId(ResourceParser.BLUE);
						} else if (x * 2 / 5 < xTouch && xTouch <= x * 3 / 5)
						{
							iv0.setVisibility(View.INVISIBLE);
							iv1.setVisibility(View.INVISIBLE);
							iv2.setVisibility(View.VISIBLE);
							iv3.setVisibility(View.INVISIBLE);
							iv4.setVisibility(View.INVISIBLE);
							ShareprefenceUtils.getInstance(mContext).setNoteBgSelectedId(ResourceParser.WHITE);
						} else if (x * 3 / 5 < xTouch && xTouch < x * 4 / 5)
						{
							iv0.setVisibility(View.INVISIBLE);
							iv1.setVisibility(View.INVISIBLE);
							iv2.setVisibility(View.INVISIBLE);
							iv3.setVisibility(View.VISIBLE);
							iv4.setVisibility(View.INVISIBLE);
							ShareprefenceUtils.getInstance(mContext).setNoteBgSelectedId(ResourceParser.GREEN);
						} else
						{
							iv0.setVisibility(View.INVISIBLE);
							iv1.setVisibility(View.INVISIBLE);
							iv2.setVisibility(View.INVISIBLE);
							iv3.setVisibility(View.INVISIBLE);
							iv4.setVisibility(View.VISIBLE);
							ShareprefenceUtils.getInstance(mContext).setNoteBgSelectedId(ResourceParser.RED);
						}
						mHandler.sendEmptyMessage(-1);//activiyt RESULT_OK=-1
						mPopupWindow.dismiss();
						break;
					}
					return false;
				}
			});
			mPopupWindow.setFocusable(true);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
			mPopupWindow.showAtLocation(mView, Gravity.RIGHT | Gravity.TOP, 20, 180);

		}
	}

	public boolean isShow()
	{
		return mPopupWindow != null && mPopupWindow.isShowing();
	}

	public void closePop()
	{
		if (mPopupWindow != null && mPopupWindow.isShowing())
		{
			mPopupWindow.dismiss();
		}
	}
}
