package com.jiangw.utils;

import android.annotation.SuppressLint;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils
{
	private static SimpleDateFormat sdf;

	/**
	 * 
	 * @return 2015-01-21 15:20:20
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getSysYearMonthDay()
	{
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		return sdf.format(date);
	}
	/**
	 * 
	 * @param time 需要格式化 的时间
	 * @return 2015-01-01 01:01:01
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getYearMonthDay(long time){
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		return sdf.format(date);
	}
	@SuppressLint("SimpleDateFormat")
	public static String getYearMonthDayNoSecond(long time){
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date(time);
		return sdf.format(date);
	}
	
	/**
	 * 
	 * @param timeStr 需要转换为long格式 的时间
	 * @return 1422322654497
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getLongYearMonthDay(String timeStr)
	{
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time = System.currentTimeMillis();
		try
		{
			time = sdf.parse(timeStr).getTime();
		} catch (ParseException e)
		{
			time = System.currentTimeMillis();
			e.printStackTrace();
		}
		return time;
	}
	public static long getLongYearMonthDayTillMins(String timeStr)
	{
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long time = System.currentTimeMillis();
		try
		{
			time = sdf.parse(timeStr).getTime();
		} catch (ParseException e)
		{
			time = System.currentTimeMillis();
			e.printStackTrace();
		}
		return time;
	}
}
