package com.mauriciotogneri.shoppingcart.model;

import java.io.Serializable;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

public class Category extends Model implements Serializable
{
	private static final long serialVersionUID = -5621512578788797213L;
	
	@Column(name = "name")
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
	
	public void update(String name)
	{
		this.name = name;
		
		save();
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
	public static boolean existsWithName(String name)
	{
		Category category = new Select().from(Category.class).where("name = ?", name).executeSingle();
		
		return category != null;
	}
}