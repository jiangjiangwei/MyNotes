package com.jiangw.db;

import com.jiangw.utils.Logs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteHelper extends SQLiteOpenHelper
{
	private static final String TAG = "NoteHelper";

	public NoteHelper(Context context)
	{
		super(context, NoteConstant.DB_NAME, null, NoteConstant.DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO Auto-generated method stub
		Logs.i(TAG, "数据库创建");
		System.out.println("o"+NoteConstant.Notes.CREATE_TABLE);
		db.execSQL(NoteConstant.Notes.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub
		Logs.i(TAG, "数据库更新");
		db.execSQL(NoteConstant.Notes.DELETE_TABLE);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub
		// super.onDowngrade(db, oldVersion, newVersion);
		onUpgrade(db, oldVersion, newVersion);
	}
}
