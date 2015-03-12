package com.jiangw.note;


import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.capricorn.ArcMenu;
import com.jiangw.adapter.NoteAdapter;
import com.jiangw.base.AppConfig;
import com.jiangw.db.NoteConstant;
import com.jiangw.listviewanimations.adapter.SwingLeftInAnimationAdapter;
import com.jiangw.note.items.EditActivity;
import com.jiangw.utils.TimeUtils;
import com.jiangw.utils.ToastUitls;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor>
{
	private SwingLeftInAnimationAdapter mAnimationAdapter;
	private ListView mListView;
	private NoteAdapter adapter;
	private static final String TAG = "MainActivity";
	private static final int[] ITEM_DRAWABLES = { R.drawable.composer_camera, R.drawable.composer_music, R.drawable.composer_place };
	private ArcMenu arcMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		swingLeftInAnimationAdapter();
		initListener();
		registerForContextMenu(mListView);
	}

	private void initView()
	{
		mListView = (ListView) findViewById(R.id.lv_main_listview);
		adapter = new NoteAdapter(this, null, true);
		arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
		getLoaderManager().initLoader(0, null, this);

	}

	private void initListener()
	{
		mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, EditActivity.class);
				intent.putExtra(AppConfig.NEW_OR_UPDATE_ID, id);
				startActivity(intent);

			}
		});
		final int itemCount = ITEM_DRAWABLES.length;
		for (int i = 0; i < itemCount; i++)
		{
			ImageView item = new ImageView(this);
			item.setImageResource(ITEM_DRAWABLES[i]);
			final int position = i;
			arcMenu.addItem(item, new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					switch (position)
					{
					case 0:
						Intent intent = new Intent(MainActivity.this, EditActivity.class);
						startActivity(intent);
						break;
					case 1:
						ToastUitls.tS(MainActivity.this, "导入文本..");
						break;
					case 2:
						ToastUitls.tS(MainActivity.this, "全局搜索..");
						break;
					}
				}
			});
		}
	}

	private void swingLeftInAnimationAdapter()
	{
		// TODO Auto-generated method stub
		mAnimationAdapter = new SwingLeftInAnimationAdapter(adapter);
		// 1.普通listview
		mAnimationAdapter.setAbsListView(mListView);
		mListView.setAdapter(mAnimationAdapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		menu.setHeaderTitle("管理");
		menu.add(0, 1, Menu.CATEGORY_SYSTEM, "删除");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		getContentResolver().delete(Uri.withAppendedPath(NoteConstant.Notes.TAB_NOTES_URI, info.id + ""), null, null);
		return super.onContextItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		// TODO Auto-generated method stub
//		return new CursorLoader(MainActivity.this, NoteConstant.Notes.TAB_NOTES_URI, null, null, null, NoteConstant.Notes.NOTE_TAB_ID + " desc");
		return new CursorLoader(MainActivity.this,NoteConstant.Notes.TAB_NOTES_URI,null,null,null,NoteConstant.Notes.NOTE_DATE_COLUMN+" desc ");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		// TODO Auto-generated method stub
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		// TODO Auto-generated method stub
		adapter.swapCursor(null);
	}
}
