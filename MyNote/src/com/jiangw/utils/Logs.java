package com.jiangw.utils;

public class Logs
{
	private static boolean flag = false;

	public static void i(String s, String str)
	{
		if (flag)
		{
			Logs.i(s, str);
		}
	}
}
