package com.jiangw.db;

import android.net.Uri;
import android.provider.BaseColumns;

public final class NoteConstant
{
	public static final String AUTHORITY = "com.jiangw.note";
	public static final String DB_NAME = "note.db";
	public static final int DB_VERSION = 1;
	private static final String CONTENT = "content://";
	private static final String SEPERATOR = "/";
	private static final String _TEXT = " text";
	private static final String _INT = " integer";
	private static final String _DOT = " ,";

	public final static class Notes implements BaseColumns
	{
		// authority = com.jiangw.note.notes
		public static final String NOTE_TAB_ID = "_id";/**唯一标识*/
		public static final String NOTE_TAB_NAME = "tab_notes";/**表名*/
		public static final String NOTE_TITLE_COLUMN = "title";/**标题*/
		public static final String NOTE_DATE_COLUMN = "date";/**日期*/
		public static final String NOTE_BGCOLOR_COLUMN = "bgColor";/**背景颜色*/
		public static final String NOTE_CONTENT_COLUMN = "content";/**便签内容*/
		public static final String NOTE_ALERT_CLOCK_COLUMN = "alert_clock";/**闹钟时间*/
		public static final String NOTE_CLOCK_STATE_COLUMN = "clock_state";/**闹钟状态，0关闭1开启*/
		public static final String NOTE_OTHER_COLUMN = "other";/**其他*/
		public static final String NOTE_AUTHORITY = AUTHORITY + ".notes";
		/** TAB_NOTES BASE URI */
		public static final Uri CONTENT_URI = Uri.parse(CONTENT + NOTE_AUTHORITY);
		/** MIME type for a content URI 返回 multiple rows */
		public static final String MIME_TYPE_ROWS = "vnd.android.cursor.dir/vnd.com.jiangw.note.notes";
		/** MIME type for a content URI that would 返回 a single row */
		public static final String MIME_TYPE_SINGLE_ROW = "vnd.android.cursor.item/vnd.com.jiangw.note";
		/** Message table primary key column name */
		public static final String ROW_ID = BaseColumns._ID;
		/** TAB_NOTES URI */
		public static final Uri TAB_NOTES_URI = Uri.withAppendedPath(CONTENT_URI, NOTE_TAB_NAME);
		// 创建表sql语句
		public static final String CREATE_TABLE = "CREATE TABLE " + NOTE_TAB_NAME + " ( " + NOTE_TAB_ID + _INT + " PRIMARY KEY autoincrement" + _DOT + NOTE_TITLE_COLUMN + _TEXT + _DOT
				+ NOTE_DATE_COLUMN + _INT + _DOT + NOTE_ALERT_CLOCK_COLUMN + _INT + _DOT + NOTE_CLOCK_STATE_COLUMN + _INT + _DOT + NOTE_BGCOLOR_COLUMN + _INT + _DOT + NOTE_CONTENT_COLUMN + _TEXT
				+ _DOT + NOTE_OTHER_COLUMN + _TEXT + " );";
		// 删除表 sql语句
		public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + NOTE_TAB_NAME;
	}
}
