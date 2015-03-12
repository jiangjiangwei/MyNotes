package com.jiangw.model;

import java.io.Serializable;

public class Note implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private int bgColor;
	private String date;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getBgColor()
	{
		return bgColor;
	}

	public void setBgColor(int bgColor)
	{
		this.bgColor = bgColor;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	@Override
	public String toString()
	{
		return "Note [title=" + title + ", bgColor=" + bgColor + ", date=" + date + "]";
	}

}
