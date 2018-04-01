package com.mauriciotogneri.shoppinglist.activities;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.utils.Analytics;
import com.mauriciotogneri.shoppinglist.views.MainView;
import com.mauriciotogneri.shoppinglist.views.MainView.MainViewObserver;

public class MainActivity extends BaseActivity<MainView> implements MainViewObserver
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
    protected MainView view()
    {
        return new MainView(this);
    }
}