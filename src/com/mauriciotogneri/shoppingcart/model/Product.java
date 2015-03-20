package com.mauriciotogneri.shoppingcart.model;

import java.util.List;
import android.util.Base64;
import com.orm.SugarRecord;

public class Product extends SugarRecord<Product>
{
	public static final int IMAGE_SIZE = 96;
	
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
	
	public boolean isInCart()
	{
		List<CartItem> cartItems = SugarRecord.find(CartItem.class, "product = ?", String.valueOf(getId()));
		
		return (!cartItems.isEmpty());
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
		return SugarRecord.findById(Product.class, id);
	}
	
	public static boolean exists(String name, Category category)
	{
		List<Product> products = SugarRecord.find(Product.class, "(name = ?) AND (category = ?)", name, String.valueOf(category.getId()));
		
		return (!products.isEmpty());
	}
	
	public static boolean exists(Category category)
	{
		List<Product> products = SugarRecord.find(Product.class, "category = ?", String.valueOf(category.getId()));
		
		return (!products.isEmpty());
	}
}