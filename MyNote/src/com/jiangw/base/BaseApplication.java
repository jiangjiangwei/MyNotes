package com.jiangw.base;

import android.app.Activity;
import android.app.Application;
import android.util.SparseArray;

public class BaseApplication extends Application
{
	public static Activity currentActivity;
	public static SparseArray<Activity> historyActivity = new SparseArray<Activity>();

	public void setInstance(Activity activity)
	{
		// System.out.println("this" + instance);
		historyActivity.append(activity.getTaskId(), activity);
		currentActivity = activity;
	}

	public Activity getActivity()
	{
		return currentActivity;
	}

	public void removeHisInstance(Activity activity)
	{
		historyActivity.remove(activity.getTaskId());
	}

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		AppConfig.app = this;
	}
}
