package com.mauriciotogneri.shoppinglist.model;

import android.text.TextUtils;

public class Product
{
    private final String name;
    private final Category category;
    private final String image;
    private boolean selected;

    public Product(String name, Category category, String image, boolean selected)
    {
        this.name = name;
        this.category = category;
        this.image = image;
        this.selected = selected;
    }

    public String name()
    {
        return name;
    }

    public Category category()
    {
        return category;
    }

    public String image()
    {
        return image;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void selected(boolean value)
    {
        selected = value;
    }

    public void toggleSelection()
    {
        selected = !selected;
    }

    public boolean isValid()
    {
        return (!TextUtils.isEmpty(name) && (category != null) && (category.isValid()) && !TextUtils.isEmpty(image));
    }
}