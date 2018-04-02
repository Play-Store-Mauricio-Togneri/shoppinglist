package com.mauriciotogneri.shoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mauriciotogneri.shoppinglist.model.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract ProductDao productDao();

    public static AppDatabase instance(Context context)
    {
        return Room.databaseBuilder(context, AppDatabase.class, "database").build();
    }

    public void initialize()
    {
        String beverages = "Beverages";
        Product beer = new Product(beverages, "Beer", "http://i.imgur.com/WmfEenJ.png", false);
        Product coffee = new Product(beverages, "Coffee", "http://i.imgur.com/aGiXxjf.png", false);
        Product iceTea = new Product(beverages, "Ice Tea", "http://i.imgur.com/Spi8duE.png", false);
        Product soda = new Product(beverages, "Soda", "http://i.imgur.com/jybjFMf.png", false);
        Product water = new Product(beverages, "Water", "http://i.imgur.com/h34kKfv.png", false);

        String breadAndGrain = "Bread & Grain Products";
        Product baguette = new Product(breadAndGrain, "Baguette", "http://i.imgur.com/6tf1nXp.png", false);
        Product cereals = new Product(breadAndGrain, "Cereals", "http://i.imgur.com/cjxBkMd.png", false);
        Product pasta = new Product(breadAndGrain, "Pasta", "http://i.imgur.com/VV4Z96m.png", false);
        Product rice = new Product(breadAndGrain, "Rice", "http://i.imgur.com/wOJ6XaT.png", false);
        Product risotto = new Product(breadAndGrain, "Risotto", "http://i.imgur.com/7dpAjjm.png", false);
        Product slicedBread = new Product(breadAndGrain, "Sliced bread", "http://i.imgur.com/Si3TyUt.png", false);

        ProductDao productDao = productDao();
        productDao.insertAll(beer, coffee, iceTea, soda, water);
        productDao.insertAll(baguette, cereals, pasta, rice, risotto, slicedBread);
    }
}