package com.mauriciotogneri.shoppingcart.model;

import android.util.Base64;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

public class Product extends Model
{
	@Column(name = "name")
	private String name;
	
	@Column(name = "category")
	private Category category;
	
	@Column(name = "picture")
	private String picture;
	
	public Product()
	{
	}
	
	public Product(String name, Category category, byte[] picture)
	{
		this.name = name;
		this.category = category;
		this.picture = Base64.encodeToString(picture, Base64.DEFAULT);
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
	
	public boolean isInCart()
	{
		CartItem cartItem = new Select().from(CartItem.class).where("product = ?", getId()).executeSingle();
		
		return (cartItem != null);
	}
	
	public byte[] getPicture()
	{
		return Base64.decode(this.picture, Base64.DEFAULT);
	}
}