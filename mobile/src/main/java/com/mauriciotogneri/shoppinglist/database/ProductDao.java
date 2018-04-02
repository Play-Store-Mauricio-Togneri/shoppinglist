package com.mauriciotogneri.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.mauriciotogneri.shoppinglist.model.Product;

import java.util.List;

@Dao
public interface ProductDao
{
    @Query("SELECT * FROM Product")
    List<Product> all();

    @Query("SELECT * FROM Product WHERE selected")
    List<Product> selected();

    @Query("SELECT * FROM Product WHERE NOT selected")
    List<Product> unselected();

    @Insert
    void insertAll(Product... products);

    @Delete
    void delete(Product product);
}