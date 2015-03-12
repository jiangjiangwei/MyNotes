package com.jiangw.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUitls
{
	private static boolean isShow = true;

	public static void tS(Context context, String msg)
	{
		if (isShow)
		{
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	public static void tL(Context context, String msg)
	{
		if (isShow)
		{
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

	public static void tD(Context context, String msg, int duration)
	{
		if (isShow)
		{
			Toast.makeText(context, msg, duration).show();
		}
	}
}
