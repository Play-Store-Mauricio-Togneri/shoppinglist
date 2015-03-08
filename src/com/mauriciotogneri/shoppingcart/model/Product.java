package com.mauriciotogneri.shoppingcart.model;

import com.activeandroid.Model;

public class Product extends Model
{
	private String name;
	private Category category;
	private byte[] picture;
	
	public Product()
	{
	}
	
	public Product(String name, Category category, byte[] picture)
	{
		this.name = name;
		this.category = category;
		this.picture = picture;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Category getCategory()
	{
		return this.category;
	}
	
	public byte[] getPicture()
	{
		return this.picture;
	}
}