package com.mauriciotogneri.common.api;

import java.io.Serializable;

public class CartElement implements Serializable
{
    public final long id;
    public final String name;
    public final String category;
    public final byte[] picture;
    public boolean isSelected;

    public CartElement(long id, String name, String category, byte[] picture, boolean isSelected)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.picture = picture;
        this.isSelected = isSelected;
    }
}