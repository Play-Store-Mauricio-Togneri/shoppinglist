package com.mauriciotogneri.shoppinglist.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.mauriciotogneri.common.utils.ImageHelper;
import com.mauriciotogneri.shoppinglist.R;
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

    public String getName()
    {
        return name;
    }

    public Category getCategory()
    {
        return category;
    }

    public byte[] getImage(Context context)
    {
        try
        {
            return Base64.decode(image, Base64.DEFAULT);
        }
        catch (Exception e)
        {
            return ImageHelper.getImageBytesFromResource(context, R.drawable.product_generic);
        }
    }

    public boolean isValid()
    {
        return (!TextUtils.isEmpty(name) && (category != null) && (category.isValid()) && !TextUtils.isEmpty(image));
    }

    public void update(String newName, Category newCategory, byte[] newImage)
    {
        name = newName;
        category = newCategory;
        image = Base64.encodeToString(newImage, Base64.DEFAULT);

        save();
    }
}