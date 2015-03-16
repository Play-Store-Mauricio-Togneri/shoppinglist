package com.mauriciotogneri.shoppingcart.model;

import java.util.List;
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
	private long categoryId;
	
	@Column(name = "image")
	private String image;
	
	private Category category;
	
	public Product()
	{
	}
	
	public Product(String name, Category category, byte[] image)
	{
		this.name = name;
		this.categoryId = category.getId();
		this.image = Base64.encodeToString(image, Base64.DEFAULT);
	}
	
	private void setCategory()
	{
		if (this.category == null)
		{
			this.category = new Select().from(Category.class).where("id = ?", this.categoryId).executeSingle();
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Category getCategory()
	{
		setCategory();
		
		return this.category;
	}
	
	public boolean isCategory(Category category)
	{
		setCategory();
		
		return this.category.getName().equals(category.getName());
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
		this.categoryId = category.getId();
		this.image = Base64.encodeToString(image, Base64.DEFAULT);
		this.category = null;
		
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
	
	public static boolean exists(Category category)
	{
		List<Product> products = new Select().from(Product.class).where("category = ?", category.getId()).execute();
		
		return (products != null) && (!products.isEmpty());
	}
}