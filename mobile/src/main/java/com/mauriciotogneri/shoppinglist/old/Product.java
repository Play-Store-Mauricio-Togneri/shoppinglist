package com.mauriciotogneri.shoppinglist.old;

import android.util.Base64;

import com.orm.SugarRecord;

public class Product extends SugarRecord<Product>
{
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

    public String getCategory()
    {
        return category.getName();
    }

    public String getName()
    {
        return name;
    }

    public byte[] getImage()
    {
        try
        {
            return Base64.decode(image, Base64.DEFAULT);
        }
        catch (Exception e)
        {
            return new byte[0];
        }
    }
}