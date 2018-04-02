package com.mauriciotogneri.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.text.TextUtils;

import java.io.Serializable;

@Entity
public class Product implements Serializable
{
    @PrimaryKey
    public Integer id;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String image;

    @ColumnInfo
    public Boolean selected;

    public Product(String category, String name, String image, Boolean selected)
    {
        this.name = name;
        this.category = category;
        this.image = image;
        this.selected = selected;
    }

    public String category()
    {
        return category;
    }

    public String name()
    {
        return name;
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
        return (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(image));
    }
}