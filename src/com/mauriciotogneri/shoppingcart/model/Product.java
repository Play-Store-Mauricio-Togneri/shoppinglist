package com.mauriciotogneri.shoppingcart.model;

import android.util.Base64;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

public class Product extends Model
{
	public static final int IMAGE_SIZE = 96;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "category")
	private Category category;
	
	@Column(name = "image")
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
		// TODO: override equals?
		return this.category.equals(category);
	}
	
	public boolean isInCart()
	{
		CartItem cartItem = new Select().from(CartItem.class).where("product = ?", getId()).executeSingle();
		
		return (cartItem != null);
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
	
	public static Product byId(long id)
	{
		return new Select().from(Product.class).where("id = ?", id).executeSingle();
	}
	
	public static boolean exists(String name, Category category)
	{
		Product product = new Select().from(Product.class).where("(name = ?) AND (category = ?)", name, category.getId()).executeSingle();
		
		return (product != null);
	}
}