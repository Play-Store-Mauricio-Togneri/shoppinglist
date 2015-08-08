package com.mauriciotogneri.shoppingcart.model;

import android.util.Base64;
import com.orm.SugarRecord;

public class Product extends SugarRecord<Product>
{
	private String name;
	private Category category;
	private String image;
	
	public Product()
	{
	}
	
	public Product(String name, Category category, byte[] image)
	{
		this.name = name;
		this.category = category;
		this.image = Base64.encodeToString(image, Base64.DEFAULT);
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
		return this.category.getName().equals(category.getName());
	}
	
	public byte[] getImage()
	{
		return Base64.decode(this.image, Base64.DEFAULT);
	}
	
	public void update(String name, Category category, byte[] image)
	{
		this.name = name;
		this.category = category;
		this.image = Base64.encodeToString(image, Base64.DEFAULT);
		
		save();
	}
}