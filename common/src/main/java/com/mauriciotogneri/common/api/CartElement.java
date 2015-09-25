package com.mauriciotogneri.common.api;

import java.io.Serializable;

public class CartElement implements Serializable
{
    private static final long serialVersionUID = -5621512578900797213L;

    public final String name;

    public CartElement(String name)
    {
        this.name = name;
    }
}