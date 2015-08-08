package com.mauriciotogneri.shoppingcart.dao;

import java.util.List;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.orm.SugarRecord;
import com.orm.query.Select;

public class CategoryDao
{
	public List<Category> getCategories()
	{
		return Select.from(Category.class).orderBy("name").list();
	}
	
	public boolean exists(String name)
	{
		List<Category> categories = SugarRecord.find(Category.class, "name = ?", name);
		
		return (!categories.isEmpty());
	}
}