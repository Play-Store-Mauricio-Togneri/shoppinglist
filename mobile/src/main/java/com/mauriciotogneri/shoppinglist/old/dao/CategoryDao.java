package com.mauriciotogneri.shoppinglist.old.dao;

import com.mauriciotogneri.shoppinglist.old.model.Category;
import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

public class CategoryDao
{
    public List<Category> getCategories()
    {
        List<Category> categories = Select.from(Category.class).orderBy("name").list();

        List<Category> validCategories = new ArrayList<>();

        for (Category category : categories)
        {
            if (category.isValid())
            {
                validCategories.add(category);
            }
        }

        return validCategories;
    }

    public int getNumberOfCategories()
    {
        return getCategories().size();
    }

    public boolean exists(String name)
    {
        List<Category> categories = SugarRecord.find(Category.class, "name = ?", name);

        return (!categories.isEmpty());
    }
}