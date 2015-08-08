package com.mauriciotogneri.shoppingcart.model;

import java.io.Serializable;
import android.graphics.Color;
import com.orm.SugarRecord;

public class Category extends SugarRecord<Category> implements Serializable
{
	private static final long serialVersionUID = -5621512578788797213L;
	
	public static final String COLOR_1 = "B4B4B4";
	public static final String COLOR_2 = "3CAAE6";
	public static final String COLOR_3 = "64C864";
	public static final String COLOR_4 = "F0583D";
	public static final String COLOR_5 = "F05A8C";
	public static final String COLOR_6 = "D6916B";
	public static final String COLOR_7 = "FFBB33";
	public static final String COLOR_8 = "B67BD2";
	
	private String name;
	private String color;
	
	public Category()
	{
	}
	
	public Category(String name, String color)
	{
		this.name = name;
		this.color = color;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getColor()
	{
		return this.color;
	}
	
	public int getIntColor()
	{
		return Color.parseColor("#" + this.color);
	}
	
	public void update(String name, String color)
	{
		this.name = name;
		this.color = color;
		
		save();
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}