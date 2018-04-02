package com.mauriciotogneri.shoppinglist.database;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.model.Products;

public class LoadProductsByCategory extends AsyncTask<Void, Void, Products>
{
    private final ProductDao dao;
    private final OnProductsLoaded callback;

    public LoadProductsByCategory(Context context, OnProductsLoaded callback)
    {
        this.dao = AppDatabase.instance(context).productDao();
        this.callback = callback;
    }

    @Override
    protected Products doInBackground(Void... voids)
    {
        Products products = new Products();

        for (Product product : dao.all())
        {
            products.add(product);
        }

        return products;
    }

    @Override
    protected void onPostExecute(Products products)
    {
        callback.onProductsLoaded(products);
    }

    public interface OnProductsLoaded
    {
        void onProductsLoaded(Products products);
    }
}