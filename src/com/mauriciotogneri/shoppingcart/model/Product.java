package com.mauriciotogneri.shoppingcart.model;

import com.activeandroid.Model;

public class Product extends Model
{
	private String name;
	private long categoryId;
	private byte[] picture;
	
	public Product()
	{
	}
	
	public Product(String name, long categoryId, byte[] picture)
	{
		this.name = name;
		this.categoryId = categoryId;
		this.picture = picture;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public long getCategoryId()
	{
		return this.categoryId;
	}
	
	public byte[] getPicture()
	{
		return this.picture;
	}
}