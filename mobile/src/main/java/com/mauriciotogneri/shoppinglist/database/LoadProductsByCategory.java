package com.mauriciotogneri.shoppinglist.database;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadProductsByCategory extends AsyncTask<Void, Void, Map<String, List<Product>>>
{
    private final ProductDao dao;
    private final OnProductsLoaded callback;

    public LoadProductsByCategory(Context context, OnProductsLoaded callback)
    {
        this.dao = AppDatabase.instance(context).productDao();
        this.callback = callback;
    }

    @Override
    protected Map<String, List<Product>> doInBackground(Void... voids)
    {
        Map<String, List<Product>> result = new HashMap<>();
        List<Product> products = dao.all();

        for (Product product : products)
        {
            if (result.containsKey(product.category))
            {
                result.get(product.category).add(product);
            }
            else
            {
                List<Product> list = new ArrayList<>();
                list.add(product);

                result.put(product.category, list);
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(Map<String, List<Product>> products)
    {
        callback.onProductsLoaded(products);
    }

    public interface OnProductsLoaded
    {
        void onProductsLoaded(Map<String, List<Product>> products);
    }
}