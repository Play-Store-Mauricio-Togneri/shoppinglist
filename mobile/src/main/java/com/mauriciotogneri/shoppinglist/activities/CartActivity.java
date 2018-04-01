package com.mauriciotogneri.shoppinglist.activities;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
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
    }

    @Override
    public void onAddProduct()
    {
    }

    @Override
    protected CartView view()
    {
        return new CartView(this);
    }
}