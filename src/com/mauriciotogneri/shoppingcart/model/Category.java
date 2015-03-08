package com.mauriciotogneri.shoppingcart.model;

import com.activeandroid.Model;

public class Category extends Model
{
	private String name;
	
	public Category()
	{
	}
	
	public Category(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
}