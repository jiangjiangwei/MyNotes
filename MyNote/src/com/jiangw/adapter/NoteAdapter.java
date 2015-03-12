package com.jiangw.adapter;

import com.jiangw.db.NoteConstant;
import com.jiangw.note.R;
import com.jiangw.utils.ResourceParser;
import com.jiangw.utils.TimeUtils;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * mainActivity listview adapter
 * 
 * @author jiangw
 * 
 */
public class NoteAdapter extends CursorAdapter
{
	private Cursor mCursor;
	private Context mContext;
	private LayoutInflater inflater;
	private static boolean isScale = false;
	private int lastIndex;
	private int bgResource;

	public NoteAdapter(Context context, Cursor c, boolean autoRequery)
	{
		super(context, c, autoRequery);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mCursor = c;
		this.inflater = LayoutInflater.from(context);
	}


	class ViewHolder
	{
		LinearLayout llMain;
		TextView tvTitle;
		TextView tvDate;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		// TODO Auto-generated method stub
//		LinearLayout llMain = (LinearLayout) view.findViewById(R.id.ll_main_listview_item_main);
		RelativeLayout llMain = (RelativeLayout)view.findViewById(R.id.ll_main_listview_item_main);
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_main_listview_item_title);
		TextView tvDate = (TextView) view.findViewById(R.id.tv_main_listview_item_date);
//		lastIndex = cursor.getCount();
//		if (lastIndex == 1)
//		{
//			bgResource = ResourceParser.NoteItemBgResource.getNoteBgSingleRes(cursor.getInt(cursor.getColumnIndex(NoteConstant.Notes.NOTE_BGCOLOR_COLUMN)));
//		} else if (lastIndex > 1)
//		{
//			if (0 == cursor.getPosition())
//			{
//				bgResource = ResourceParser.NoteItemBgResource.getNoteBgUpRes(cursor.getInt(cursor.getColumnIndex(NoteConstant.Notes.NOTE_BGCOLOR_COLUMN)));
//			} else if ((lastIndex - 1) == cursor.getPosition())
//			{
//				bgResource = ResourceParser.NoteItemBgResource.getNoteBgDownRes(cursor.getInt(cursor.getColumnIndex(NoteConstant.Notes.NOTE_BGCOLOR_COLUMN)));
//			} else
//			{
//				bgResource = ResourceParser.NoteItemBgResource.getNoteBgNormalRes(cursor.getInt(cursor.getColumnIndex(NoteConstant.Notes.NOTE_BGCOLOR_COLUMN)));
//			}
//		}
		bgResource = ResourceParser.NoteItemBgResource.getNoteBgNormalRes(cursor.getInt(cursor.getColumnIndex(NoteConstant.Notes.NOTE_BGCOLOR_COLUMN)));

		llMain.setBackgroundResource(bgResource);
		tvTitle.setText(cursor.getString(cursor.getColumnIndex(NoteConstant.Notes.NOTE_CONTENT_COLUMN)));
		tvDate.setText(TimeUtils.getYearMonthDay(cursor.getLong(cursor.getColumnIndex(NoteConstant.Notes.NOTE_DATE_COLUMN))));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2)
	{
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.main_listview_item, arg2, false);
	}
}
