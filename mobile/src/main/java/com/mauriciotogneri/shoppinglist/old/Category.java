package com.mauriciotogneri.shoppinglist.old;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Category extends SugarRecord<Category> implements Serializable
{
    private String name;
    private String color;

    public Category()
    {
    }

    public Category(String name, String color)
    {
        this.name = name;
        this.color = color;
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }
}