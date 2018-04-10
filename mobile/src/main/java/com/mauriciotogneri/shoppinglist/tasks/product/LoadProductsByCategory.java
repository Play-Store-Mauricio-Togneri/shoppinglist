package com.mauriciotogneri.shoppinglist.tasks.product;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Product;

import java.util.Collections;
import java.util.List;

public class LoadProductsByCategory extends AsyncTask<Void, Void,  List<Product>>
{
    private final ProductDao dao;
    private final String category;
    private final OnProductsLoaded callback;

    public LoadProductsByCategory(Context context, String category, OnProductsLoaded callback)
    {
        this.dao = ProductDao.instance(context);
        this.category = category;
        this.callback = callback;
    }

    @Override
    protected  List<Product> doInBackground(Void... voids)
    {
        List<Product> products = dao.byCategory(category);
        Collections.sort(products, (p1, p2) -> p1.name().compareTo(p2.name()));

        return products;
    }

    @Override
    protected void onPostExecute( List<Product> products)
    {
        callback.onProductsLoaded(products);
    }

    public interface OnProductsLoaded
    {
        void onProductsLoaded( List<Product> products);
    }
}