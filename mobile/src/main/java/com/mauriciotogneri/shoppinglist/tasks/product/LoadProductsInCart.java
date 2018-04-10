package com.mauriciotogneri.shoppinglist.tasks.product;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Product;

import java.util.List;

public class LoadProductsInCart extends AsyncTask<Void, Void, List<Product>>
{
    private final ProductDao dao;
    private final OnProductsLoaded callback;

    public LoadProductsInCart(Context context, OnProductsLoaded callback)
    {
        this.dao = ProductDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected List<Product> doInBackground(Void... voids)
    {
        return dao.inCart();
    }

    @Override
    protected void onPostExecute(List<Product> products)
    {
        callback.onProductsLoaded(products);
    }

    public interface OnProductsLoaded
    {
        void onProductsLoaded(List<Product> products);
    }
}