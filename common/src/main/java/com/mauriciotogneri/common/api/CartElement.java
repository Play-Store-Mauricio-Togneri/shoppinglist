package com.mauriciotogneri.common.api;

import java.io.Serializable;

public class CartElement implements Serializable
{
    private final Integer id;
    private final String category;
    private final String name;
    private final String image;
    private Boolean isSelected;

    public CartElement(Integer id, String category, String name, String image)
    {
        this.id = id;
        this.category = category;
        this.name = name;
        this.image = image;
        this.isSelected = false;
    }

    public Integer id()
    {
        return id;
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

    public void toggleSelection()
    {
        isSelected = !isSelected;
    }

    public Boolean isSelected()
    {
        return isSelected;
    }
}