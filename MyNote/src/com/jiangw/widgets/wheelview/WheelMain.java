package com.jiangw.widgets.wheelview;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import android.view.View;
import com.jiangw.note.R;
import com.jiangw.utils.TimeUtils;

public class WheelMain
{

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	private WheelView wv_second;
	private static int START_YEAR = 1990, END_YEAR = 2100;
	private int year, month, day, hour, min, second;

	public View getView()
	{
		return view;
	}

	public void setView(View view)
	{
		this.view = view;
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

	public WheelMain(View view)
	{
		super();

		this.view = view;
		setView(view);
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void initDateTimePicker(long d)
	{
		Calendar calendar = Calendar.getInstance();

		// 采用date时间
		if (d != 0)
		{
			calendar.setTimeInMillis(d);
		}
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DATE);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		min = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		// 时
		wv_hours = (WheelView) view.findViewById(R.id.hours);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 24));
		wv_hours.setCyclic(true);
		wv_hours.setLabel("时");
		wv_hours.setCurrentItem(hour);

		// 分
		wv_mins = (WheelView) view.findViewById(R.id.min);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 60));
		wv_mins.setCyclic(true);
		wv_mins.setLabel("分");
		wv_mins.setCurrentItem(min);

		// 秒
		wv_second = (WheelView) view.findViewById(R.id.second);
		wv_second.setAdapter(new NumericWheelAdapter(0, 60));
		wv_second.setCyclic(true);
		wv_second.setLabel("秒");
		wv_second.setCurrentItem(second);

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener()
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1)))
				{
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1)))
				{
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else
				{
					if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener()
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num)))
				{
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num)))
				{
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else
				{
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year.getCurrentItem() + START_YEAR) % 100 != 0) || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};

		// 添加"时"监听
		OnWheelChangedListener wheelListener_hour = new OnWheelChangedListener()
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				// 确定"时"的数据
				wv_hours.setAdapter(new NumericWheelAdapter(1, 24));
			}
		};
		OnWheelChangedListener wheelListener_min = new OnWheelChangedListener()
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				// 确定 分 的数据
				wv_mins.setAdapter(new NumericWheelAdapter(1, 60));
			}
		};
		OnWheelChangedListener wheelListener_second = new OnWheelChangedListener()
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				// 确定 分 的数据
				wv_second.setAdapter(new NumericWheelAdapter(1, 60));
			}
		};

		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		wv_hours.addChangingListener(wheelListener_hour);
		wv_mins.addChangingListener(wheelListener_min);
		wv_second.addChangingListener(wheelListener_second);

		// 根据屏幕密度来指定选择器字体的大小
		int textSize = 0;

		textSize = 40;

		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_second.TEXT_SIZE = textSize;
	}

	public String getTime()
	{
		StringBuffer sb = new StringBuffer();
		int mInt = wv_month.getCurrentItem() + 1;
		int dInt = wv_day.getCurrentItem() + 1;
		int hInt = wv_hours.getCurrentItem();
		int minInt = wv_mins.getCurrentItem();
		int sInt = wv_second.getCurrentItem();
		String m = mInt < 10 ? "0" + mInt : mInt + "";
		String d = dInt < 10 ? "0" + dInt : dInt + "";
		String h = hInt < 10 ? "0" + hInt : hInt + "";
		String min = minInt < 10 ? "0" + minInt : minInt + "";
		String s = sInt < 10 ? "0" + sInt : sInt + "";
		sb.append((wv_year.getCurrentItem() + START_YEAR)).append("-").append(m).append("-").append(d).append(" ").append(h).append(":").append(min).append(":").append(s).append(" ");
		return sb.toString();
	}

	public long getLongTime()
	{
		return TimeUtils.getLongYearMonthDay(getTime());
	}
}
