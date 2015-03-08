package com.mauriciotogneri.shoppingcart.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class Product extends Model
{
	@Column(name = "name")
	private String name;
	
	@Column(name = "category")
	private Category category;
	
	@Column(name = "picture")
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
	
	public boolean isCategory(Category category)
	{
		// TODO: override equals?
		return this.category.equals(category);
	}
	
	public byte[] getPicture()
	{
		return this.picture;
	}
}