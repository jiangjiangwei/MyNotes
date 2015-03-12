package com.jiangw.utils;

import com.jiangw.note.R;

public class ResourceParser
{
	public static final int YELLOW 						= 0;
	public static final int BLUE 						= 1;
	public static final int WHITE 						= 2;
	public static final int GREEN 						= 3;
	public static final int RED 						= 4;
	public static final int BG_DEFAULT_COLOR 			= YELLOW;
	public static final int TEXT_SMALL 					= 0;
	public static final int TEXT_MEDIUM					= 1;
	public static final int TEXT_LARGE					= 2;
	public static final int TEXT_SUPER					= 3;
	public static final int FONT_DEFAULT_SIZE 			= TEXT_MEDIUM;
	
	public static class NoteBgResource{
		
		private static final int[] BG_EDIT_RESOURCES = new int[]{
			R.drawable.edit_yellow,
			R.drawable.edit_blue,
			R.drawable.edit_white,
			R.drawable.edit_green,
			R.drawable.edit_red
		};
		
		private static final int[] BG_TITLE_RESOURCES = new int[]{
			R.drawable.title_yellow_shape,
			R.drawable.title_blue_shape,
			R.drawable.title_white_shape,
			R.drawable.title_green_shape,
			R.drawable.title_red_shape
		};
		
		public static int getNoteBgResource(int id){
			return BG_EDIT_RESOURCES[id];
		}
		
		public static int getNoteTitleResource(int id){
			return BG_TITLE_RESOURCES[id];
		}
	}
	public static class NoteItemBgResource{
		
		private static final int[] BG_UP_RESOURCES = new int[]{
			R.drawable.list_yellow_up,
			R.drawable.list_blue_up,
			R.drawable.list_white_up,
			R.drawable.list_green_up,
			R.drawable.list_red_up
		};
		
		private static final int[] BG_NORMAL_RESOURCES =new int[]{
			R.drawable.list_yellow_middle,
			R.drawable.list_blue_middle,
			R.drawable.list_white_middle,
			R.drawable.list_green_middle,
			R.drawable.list_red_middle
		};
		
		private static final int[] BG_SINGLE_RESOURCES = new int[]{
			R.drawable.list_yellow_single,
			R.drawable.list_blue_single,
			R.drawable.list_white_single,
			R.drawable.list_green_single,
			R.drawable.list_red_single
		};
		
		private static final int[] BG_DOWN_RESOURCES = new int[]{
			R.drawable.list_yellow_down,
			R.drawable.list_blue_down,
			R.drawable.list_white_down,
			R.drawable.list_green_down,
			R.drawable.list_red_down
		};
		
		public static int getNoteBgUpRes(int id){
			return BG_UP_RESOURCES[id];
		}
		
		public static int getNoteBgNormalRes(int id){
			return BG_NORMAL_RESOURCES[id];
		}
		
		public static int getNoteBgSingleRes(int id){
			return BG_NORMAL_RESOURCES[id];
		}
		
		public static int getNoteBgDownRes(int id){
			return BG_DOWN_RESOURCES[id];
		}
	}
}
