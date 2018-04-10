package com.mauriciotogneri.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.content.Context;

import com.mauriciotogneri.shoppinglist.model.Category;

import java.util.List;

@Dao
public interface CategoryDao
{
    @Query("SELECT * FROM Category")
    List<Category> all();

    @Query("UPDATE Category SET name=:newName WHERE name=:oldName")
    void rename(String oldName, String newName);

    @Insert
    void insert(Category... categories);

    @Delete
    void delete(Category category);

    static CategoryDao instance(Context context)
    {
        return AppDatabase.instance(context).categoryDao();
    }
}