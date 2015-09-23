package com.mauriciotogneri.shoppinglist.dao;

import com.mauriciotogneri.shoppinglist.model.Category;
import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.List;

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