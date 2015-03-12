package com.jiangw.db.provider;

import com.jiangw.db.NoteConstant;
import com.jiangw.db.NoteHelper;
import com.jiangw.utils.Logs;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

public class NoteProvider extends ContentProvider
{
	private static final String TAG = "NoteProvider";

	public NoteProvider()
	{
	}

	private NoteHelper dbHelper;
	private static final UriMatcher sUriMatcher;
	private static final SparseArray<String> sMimeTypes;
	static
	{
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sMimeTypes = new SparseArray<String>();
		sUriMatcher.addURI(NoteConstant.Notes.NOTE_AUTHORITY, NoteConstant.Notes.NOTE_TAB_NAME, 1);
		sUriMatcher.addURI(NoteConstant.Notes.NOTE_AUTHORITY, NoteConstant.Notes.NOTE_TAB_NAME + "/#", 2);
		sMimeTypes.put(1, "vnd.android.cursor.dir/vnd." + NoteConstant.AUTHORITY + "." + NoteConstant.Notes.NOTE_TAB_NAME);
		sMimeTypes.put(2, "vnd.android.cursor.dir/vnd." + NoteConstant.AUTHORITY + "." + NoteConstant.Notes.NOTE_TAB_NAME);
	}

	@Override
	public boolean onCreate()
	{
		// TODO Auto-generated method stub
		Logs.i(TAG, "onCreate()");
		dbHelper = new NoteHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		// TODO Auto-generated method stub
		Logs.i(TAG, "query");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(NoteConstant.Notes.NOTE_TAB_NAME);
		switch (sUriMatcher.match(uri))
		{
		case 1:
			break;

		case 2:
			builder.appendWhere(NoteConstant.Notes.ROW_ID + "=" + uri.getLastPathSegment());
			break;
		}
		Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri)
	{
		// TODO Auto-generated method stub
		return sMimeTypes.get(sUriMatcher.match(uri));
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		// TODO Auto-generated method stub
		Logs.i(TAG, "insert");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (sUriMatcher.match(uri))
		{
		case 1:
			long id = db.insert(NoteConstant.Notes.NOTE_TAB_NAME, null, values);
			if (-1 != id)
			{
				// 绑定监听，监听数据
				getContext().getContentResolver().notifyChange(uri, null);
				return Uri.withAppendedPath(uri, Long.toString(id));
			} else
			{
				throw new SQLiteException("Insert error:" + uri);
			}
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		Logs.i(TAG, "delete");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rowDelete = 0;
		switch (sUriMatcher.match(uri))
		{
		case 1:
			rowDelete = db.delete(NoteConstant.Notes.NOTE_TAB_NAME, selection, selectionArgs);
			break;
		case 2:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection))
			{
				rowDelete = db.delete(NoteConstant.Notes.NOTE_TAB_NAME, NoteConstant.Notes.ROW_ID + " = " + id, null);
			} else
			{
				// rowDelete = db.delete(NoteConstant.Notes.NOTE_TAB_NAME,
				// NoteConstant.Notes.ROW_ID +" = "+id + "AND "+ selection,
				// null);
				rowDelete = db.delete(NoteConstant.Notes.NOTE_TAB_NAME, NoteConstant.Notes.ROW_ID + " = " + id + "AND (" + selection + " )", selectionArgs);
			}
			break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowDelete;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		Logs.i(TAG, "update");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int rowUpdate = 0;
		switch (sUriMatcher.match(uri))
		{
		case 1:
			rowUpdate = db.update(NoteConstant.Notes.NOTE_TAB_NAME, values, selection, selectionArgs);
			break;

		case 2:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection))
			{
				rowUpdate = db.delete(NoteConstant.Notes.NOTE_TAB_NAME, NoteConstant.Notes.ROW_ID + "=" + id, null);
			} else
			{
				rowUpdate = db.delete(NoteConstant.Notes.NOTE_TAB_NAME, NoteConstant.Notes.ROW_ID + "=" + id + " and (" + selection + " )", selectionArgs);
			}
			break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowUpdate;
	}

}
