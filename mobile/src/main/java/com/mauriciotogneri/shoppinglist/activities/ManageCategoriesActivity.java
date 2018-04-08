package com.mauriciotogneri.shoppinglist.activities;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.database.LoadCategories;
import com.mauriciotogneri.shoppinglist.database.LoadCategories.OnCategoriesLoaded;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView.ManageCategoriesViewObserver;

import java.util.List;

public class ManageCategoriesActivity extends BaseActivity<ManageCategoriesView> implements ManageCategoriesViewObserver, OnCategoriesLoaded
{
    @Override
    protected void initialize()
    {
        LoadCategories loader = new LoadCategories(this, this);
        loader.execute();
    }

    @Override
    public void onCategoriesLoaded(List<String> categories)
    {
        view.updateList(categories);
    }

    @Override
    public void onCategorySelected(String category)
    {
        // TODO
    }

    @Override
    public void onAddCategory()
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
        return new ManageCategoriesView(this, this);
    }
}