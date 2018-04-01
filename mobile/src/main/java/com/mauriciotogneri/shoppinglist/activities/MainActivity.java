package com.mauriciotogneri.shoppinglist.activities;

import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.app.ShoppingList;
import com.mauriciotogneri.shoppinglist.views.MainView;
import com.mauriciotogneri.shoppinglist.views.MainView.MainViewObserver;

public class MainActivity extends BaseActivity<MainView> implements MainViewObserver
{
    @Override
    protected void initialize()
    {
        ShoppingList.analytics().sendAppLaunched();
    }

    @Override
    public void onAddProduct()
    {
    }

    @Override
    protected MainView view()
    {
        return new MainView(this);
    }
}