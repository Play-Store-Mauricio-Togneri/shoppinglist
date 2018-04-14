package com.mauriciotogneri.shoppinglist.old;

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

    public Long productId()
    {
        return product.getId();
    }

    public String getCategory()
    {
        return product.getCategory();
    }

    public String getName()
    {
        return product.getName();
    }

    public byte[] getImage()
    {
        return product.getImage();
    }
}