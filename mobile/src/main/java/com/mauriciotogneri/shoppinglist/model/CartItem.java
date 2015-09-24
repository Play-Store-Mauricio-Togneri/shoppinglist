package com.mauriciotogneri.shoppinglist.model;

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

    public byte[] getImage()
    {
        return product.getImage();
    }

    public boolean isSelected()
    {
        return selected;
    }

    public Category getCategory()
    {
        return product.getCategory();
    }

    public void invertSelection()
    {
        selected = !selected;
    }
}