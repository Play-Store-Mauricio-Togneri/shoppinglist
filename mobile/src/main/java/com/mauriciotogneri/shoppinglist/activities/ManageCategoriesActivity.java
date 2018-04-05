package com.mauriciotogneri.shoppinglist.activities;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView.ManageCategoriesViewObserver;

public class ManageCategoriesActivity extends BaseActivity<ManageCategoriesView> implements ManageCategoriesViewObserver
{
    @Override
    protected void initialize()
    {
        // TODO
    }

    @Override
    public void onBack()
    {
        finish();
    }

    @Override
    protected ManageCategoriesView view()
    {
        return new ManageCategoriesView(this);
    }
}