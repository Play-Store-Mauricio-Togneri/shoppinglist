package com.mauriciotogneri.shoppinglist.tasks.product;

import android.content.Context;

import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Product;

import java.util.List;

public class UpdateProducts
{
    private final Context context;

    public UpdateProducts(Context context)
    {
        this.context = context;
    }

    public void setSelection(Product product)
    {
        new Thread(() ->
        {
            ProductDao dao = ProductDao.instance(context);
            dao.setSelection(product.id(), product.isSelected());
        }).start();
    }

    public void moveToCart(Product product)
    {
        new Thread(() ->
        {
            ProductDao dao = ProductDao.instance(context);
            dao.moveToCart(product.id(), true, false);
        }).start();
    }

    public void removeFromCart(List<Product> products)
    {
        new Thread(() ->
        {
            ProductDao dao = ProductDao.instance(context);

            for (Product product : products)
            {
                dao.moveToCart(product.id(), false, false);
            }
        }).start();
    }
}