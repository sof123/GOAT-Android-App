package com.broke.activity3;

import java.io.Serializable;

public class SearchItem implements Serializable {
	
	private String url;
	private String title;
	
	SearchItem(String title, String url)
	{
		this.url = url;
		this.title = title;
	}
	String getTitle()
	{
		return title;
	}
	String getUrl()
	{
		return url;
	}
	
	@Override
	public String toString()
	{
		return "[" + title + "]";
	}
	

}
