package com.mauriciotogneri.shoppinglist.tasks.product;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Product;

public class CreateProduct extends AsyncTask<Void, Void, Boolean>
{
    private final Product product;
    private final ProductDao dao;
    private final OnProductsCreated callback;

    public CreateProduct(Context context, Product product, OnProductsCreated callback)
    {
        this.product = product;
        this.dao = ProductDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        Boolean result = false;

        if (!dao.contains(product.name(), product.category()))
        {
            dao.insert(product);
            result = true;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        callback.onProductsCreated(result);
    }

    public interface OnProductsCreated
    {
        void onProductsCreated(Boolean result);
    }
}