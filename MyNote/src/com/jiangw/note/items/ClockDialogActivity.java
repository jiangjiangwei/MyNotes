package com.jiangw.note.items;

import com.jiangw.base.AppConfig;
import com.jiangw.base.BaseApplication;
import com.jiangw.note.R;
import com.jiangw.utils.TimeUtils;
import com.jiangw.widgets.wheelview.WheelClock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ClockDialogActivity extends Activity
{
	private View view;
	private Button btnSubmit;
	private WheelClock wClock;
	private long id;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.clock_wheel_view, null);
		setContentView(view);
		// setContentView(R.layout.clock_wheel_view);
		AppConfig.app.setInstance(this);
		initView();
		initListener();

	}

	private void initView()
	{
		String timeStr = "";
		long time = 0;
		id = getIntent().getExtras().getLong(AppConfig.UPDATE_CLOCK_TIME_ID, 0);
		try
		{
			timeStr = getIntent().getExtras().getString(AppConfig.CURRENT_CLOCK_TIME, "");
		} catch (NullPointerException e)
		{
			timeStr = "";
		}
		if (!TextUtils.isEmpty(timeStr))
		{
//			time = TimeUtils.getLongYearMonthDay(timeStr);
			time = TimeUtils.getLongYearMonthDayTillMins(timeStr);
		}
		wClock = new WheelClock(view);
		wClock.initWheelClock(time);
		btnSubmit = (Button) view.findViewById(R.id.btn_submit);

	}

	private void initListener()
	{
		btnSubmit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra(AppConfig.RETURN_CLOCK_TIME, wClock.getTime());
				intent.putExtra(AppConfig.UPDATE_CLOCK_TIME_ID, id);
				setResult(200, intent);
				closeDailogActivity();
			}
		});
	}

	/** 实现点击屏幕销毁此activity */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		closeDailogActivity();
		return true;
	}

	public void closeDailogActivity()
	{
		if (AppConfig.app.getActivity() != null)
			AppConfig.app.getActivity().finish();
		AppConfig.app.removeHisInstance(this);

		// finish();// 会调用onDestroy()方法；
	}
}
