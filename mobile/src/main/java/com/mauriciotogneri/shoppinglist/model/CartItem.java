package com.mauriciotogneri.shoppinglist.model;

import android.content.Context;

import com.orm.SugarRecord;

public class CartItem extends SugarRecord<CartItem>
{
    private Product product;
    private boolean selected;

    public CartItem()
    {
    }

    public CartItem(Product product, boolean selected)
    {
        this.product = product;
        this.selected = selected;
    }

    public String getName()
    {
        return product.getName();
    }

    public String getCategoryName()
    {
        return product.getCategory().getName();
    }

    public byte[] getImage(Context context)
    {
        return product.getImage(context);
    }

    public boolean isSelected()
    {
        return selected;
    }

    public boolean isValid()
    {
        return (product != null) && (product.isValid());
    }

    public Category getCategory()
    {
        return product.getCategory();
    }

    public void setSelected(boolean value)
    {
        selected = value;
    }

    public void invertSelection()
    {
        selected = !selected;
    }
}