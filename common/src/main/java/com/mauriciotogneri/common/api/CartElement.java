package com.mauriciotogneri.common.api;

import java.io.Serializable;

public class CartElement implements Serializable
{
    private static final long serialVersionUID = -5621512578900797213L;

    public final long id;
    public final String name;
    public final String category;
    public final byte[] picture;
    public boolean isSelected = false;

    public CartElement(long id, String name, String category, byte[] picture, boolean isSelected)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.picture = picture;
        this.isSelected = isSelected;
    }
}