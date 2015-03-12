package com.jiangw.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareprefenceUtils
{
	private Context mContext;
	private static ShareprefenceUtils instance;
	private SharedPreferences mSharedPreferences;
	private Editor mEditor;
	private static final String SHAREPREF_NAME = "note_pref";
	/** 编辑Note界面，弹出的pop所勾选背景色的id序列 */
	private static final String NOTE_BG_SELECTED_ID = "note_bg_selected_id";

	private ShareprefenceUtils(Context context)
	{
		mContext = context;
		mSharedPreferences = mContext.getSharedPreferences(SHAREPREF_NAME, Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();
	}

	public static ShareprefenceUtils getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new ShareprefenceUtils(context);
		}
		return instance;
	}

	public void setNoteBgSelectedId(int id)
	{
		mEditor.putInt(NOTE_BG_SELECTED_ID, id);
		mEditor.commit();
	}

	public int getNoteBgSelectedId()
	{
		return mSharedPreferences.getInt(NOTE_BG_SELECTED_ID, ResourceParser.BG_DEFAULT_COLOR);
	}

}
