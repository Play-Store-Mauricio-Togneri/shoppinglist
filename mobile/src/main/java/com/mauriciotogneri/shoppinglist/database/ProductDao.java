package com.mauriciotogneri.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.content.Context;

import com.mauriciotogneri.shoppinglist.model.Product;

import java.util.List;

@Dao
public interface ProductDao
{
    @Query("SELECT * FROM Product")
    List<Product> all();

    @Query("SELECT * FROM Product WHERE inCart")
    List<Product> inCart();

    @Query("SELECT * FROM Product WHERE NOT inCart")
    List<Product> notInCart();

    @Query("UPDATE Product SET inCart=:inCart WHERE id=:id")
    void moveToCart(Integer id, Boolean inCart);

    @Insert
    void insertAll(Product... products);

    @Delete
    void delete(Product product);

    static ProductDao instance(Context context)
    {
        return AppDatabase.instance(context).productDao();
    }
}