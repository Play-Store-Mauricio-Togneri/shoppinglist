package com.mauriciotogneri.shoppinglist.activities;

import android.content.Intent;
import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.database.LoadProductsInCart;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.utils.Analytics;
import com.mauriciotogneri.shoppinglist.views.CartView;
import com.mauriciotogneri.shoppinglist.views.CartView.CartViewObserver;

public class CartActivity extends BaseActivity<CartView> implements CartViewObserver
{
    @Override
    protected void initialize()
    {
        Analytics analytics = new Analytics(this);
        analytics.appLaunched();

        LoadProductsInCart loader = new LoadProductsInCart(this, products -> view.updateList(products));
        loader.execute();
    }

    @Override
    public void onProduceSelected(Product product)
    {
        product.toggleSelection();
        view.updateList();
    }

    @Override
    public void onShare()
    {
        Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddProduct()
    {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    @Override
    protected CartView view()
    {
        return new CartView(this);
    }
}