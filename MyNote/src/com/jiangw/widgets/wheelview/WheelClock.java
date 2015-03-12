package com.jiangw.widgets.wheelview;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.jiangw.note.R;
import com.jiangw.utils.TimeUtils;

import android.R.array;
import android.R.integer;
import android.R.string;
import android.view.View;

public class WheelClock
{
	private int year, month, day, hour, minute, second;
	private WheelView wv_year, wv_month, wv_day, wv_hour, wv_minute, wv_second;
	private View view;
	private static int START_YEAR = 1915;
	private static int END_YEAR = 2115;// now 2015
	private int textSize = 40;// 字体大小
	private static final String[] BIG_MONHT = { "1", "3", "5", "7", "8", "10", "12" };
	private static final String[] SMALL_MONTH = { "4", "6", "9", "11" };

	public WheelClock(View view)
	{
		this.view = view;
		setView(view);
	}

	private void setView(View view)
	{
		this.view = view;
	}

	public View getView()
	{
		return this.view;
	}

	public void initWheelClock(long d)
	{
		Calendar calendar = Calendar.getInstance();
		if (0 != d)
		{
			calendar.setTimeInMillis(d);
		}
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
		System.out.println("month="+month);
		final List<String> big_list = Arrays.asList(BIG_MONHT);
		final List<String> small_list = Arrays.asList(SMALL_MONTH);
		// 初始控件
		// wv_year = (WheelView)view.findViewById(R.id.wv_year);

		wv_month = (WheelView) view.findViewById(R.id.wv_month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setCurrentItem(month);
		wv_month.setLabel("月");

		//
		wv_day = (WheelView) view.findViewById(R.id.wv_day);
		if (big_list.contains(String.valueOf(wv_month.getCurrentItem() + 1)))
		{
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (small_list.contains(String.valueOf(wv_month.getCurrentItem() + 1)))
		{
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else
		{
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		// wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		wv_day.setCyclic(true);
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		wv_hour = (WheelView) view.findViewById(R.id.wv_hour);
		wv_hour.setAdapter(new NumericWheelAdapter(0, 24));
		wv_hour.setCyclic(true);
		wv_hour.setCurrentItem(hour);
		wv_hour.setLabel("时");

		wv_minute = (WheelView) view.findViewById(R.id.wv_min);
		wv_minute.setAdapter(new NumericWheelAdapter(0, 60));
		wv_minute.setLabel("分");
		wv_minute.setCyclic(true);
		wv_minute.setCurrentItem(minute);
		// 控件监听
		wv_month.addChangingListener(new OnWheelChangedListener()
		{

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				// TODO Auto-generated method stub
				int month_num = newValue;
				if (big_list.contains(String.valueOf(month_num + 1)))
				{
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (small_list.contains(String.valueOf(month_num + 1)))
				{
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else
				{
					if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
					{
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					} else
					{
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
			}
		});
		wv_hour.addChangingListener(new OnWheelChangedListener()
		{

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				// TODO Auto-generated method stub
				wv_hour.setAdapter(new NumericWheelAdapter(1, 60));
			}
		});
		wv_minute.addChangingListener(new OnWheelChangedListener()
		{

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				// TODO Auto-generated method stub
				wv_minute.setAdapter(new NumericWheelAdapter(1, 60));
			}
		});

		wv_month.TEXT_SIZE = textSize;
		wv_day.TEXT_SIZE = textSize;
		wv_hour.TEXT_SIZE = textSize;
		wv_minute.TEXT_SIZE = textSize;
	}

	public static int getSTART_YEAR()
	{
		return START_YEAR;
	}

	public static void setSTART_YEAR(int sTART_YEAR)
	{
		START_YEAR = sTART_YEAR;
	}

	public static int getEND_YEAR()
	{
		return END_YEAR;
	}

	public static void setEND_YEAR(int eND_YEAR)
	{
		END_YEAR = eND_YEAR;
	}

	public String getTime()
	{
		StringBuffer sb = new StringBuffer();
		int mInt = wv_month.getCurrentItem() + 1;
		int dInt = wv_day.getCurrentItem() + 1;
		int hInt = wv_hour.getCurrentItem();
		int minInt = wv_minute.getCurrentItem();
		// int sInt = wv_second.getCurrentItem();
		String m = mInt < 10 ? "0" + mInt : mInt + "";
		String d = dInt < 10 ? "0" + dInt : dInt + "";
		String h = hInt < 10 ? "0" + hInt : hInt + "";
		String min = minInt < 10 ? "0" + minInt : minInt + "";
		// String s = sInt < 10 ? "0" + sInt : sInt + "";
		sb
//		.append((wv_year.getCurrentItem() + START_YEAR)).append("-")
		.append(year).append("-").append(m).append("-").append(d).append(" ").append(h).append(":").append(min)
//		.append(":").append(s).append(" ")
		;
		return sb.toString();
	}

	public long getLongTime()
	{
		return TimeUtils.getLongYearMonthDay(getTime());
	}
}
