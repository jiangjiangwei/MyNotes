package com.jiangw.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕相关的工具类
 * 
 * @author jiangw
 * 
 */
public class ScreenTools
{
	private static ScreenTools instance;
	private Context mContext;
	private DisplayMetrics dm;

	private ScreenTools(Context context)
	{
		mContext = context;
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
	}

	public static ScreenTools getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new ScreenTools(context);
		}
		return instance;
	}

	/**
	 * 获取屏幕宽度 单位pixels（分辨率）
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度 单位pixels（分辨率）
	 * 
	 * @return
	 */
	public int getHeigth()
	{
		return dm.heightPixels;
	}

	/**
	 * dip 转换为 px
	 * 
	 * @param dipValue
	 * @return
	 */
	public int dip2px(float dipValue)
	{
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px 转换为 dip
	 * 
	 * @param pxValue
	 * @return
	 */
	public int px2dip(float pxValue)
	{
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 05.f);
	}
}
