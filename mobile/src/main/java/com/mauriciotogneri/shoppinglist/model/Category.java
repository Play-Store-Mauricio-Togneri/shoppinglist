package com.mauriciotogneri.shoppinglist.model;

import android.text.TextUtils;

public class Category
{
    private final String name;

    public Category(String name)
    {
        this.name = name;
    }

    public String name()
    {
        return name;
    }

    public boolean isValid()
    {
        return !TextUtils.isEmpty(name);
    }

    @Override
    public String toString()
    {
        return name;
    }
}