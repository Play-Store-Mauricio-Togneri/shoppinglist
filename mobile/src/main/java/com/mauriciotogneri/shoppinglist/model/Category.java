package com.mauriciotogneri.shoppinglist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Category implements Serializable
{
    @PrimaryKey
    public Integer id;

    @ColumnInfo
    public String name;

    public Category(String name)
    {
        this.name = name;
    }

    public Integer id()
    {
        return id;
    }

    public String name()
    {
        return name;
    }
}