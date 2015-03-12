package com.jiangw.note.schedule;

import com.jiangw.note.items.AlarmAlertActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver
{
	public void onReceive(android.content.Context context, android.content.Intent intent)
	{
		intent.setClass(context, AlarmAlertActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	};
}
