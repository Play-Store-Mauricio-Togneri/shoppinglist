package com.mauriciotogneri.shoppinglist.tasks.product;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Product;

public class UpdateProduct extends AsyncTask<Void, Void, Boolean>
{
    private final Product oldProduct;
    private final Product newProduct;
    private final ProductDao dao;
    private final OnProductUpdated callback;

    public UpdateProduct(Context context, Product oldProduct, Product newProduct, OnProductUpdated callback)
    {
        this.oldProduct = oldProduct;
        this.newProduct = newProduct;
        this.dao = ProductDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        Boolean result = false;

        if (!dao.contains(newProduct.name(), newProduct.category()))
        {
            dao.update(oldProduct.id(), newProduct.name(), newProduct.category(), newProduct.image());
            result = true;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        callback.onProductUpdated(result);
    }

    public interface OnProductUpdated
    {
        void onProductUpdated(Boolean result);
    }
}