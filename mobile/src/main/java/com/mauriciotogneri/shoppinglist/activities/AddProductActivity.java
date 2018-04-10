package com.mauriciotogneri.shoppinglist.activities;

import android.content.Intent;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.database.LoadCategories;
import com.mauriciotogneri.shoppinglist.database.LoadCategories.OnCategoriesLoaded;
import com.mauriciotogneri.shoppinglist.views.AddProductView;
import com.mauriciotogneri.shoppinglist.views.AddProductView.AddProductViewObserver;

import java.util.List;

public class AddProductActivity extends BaseActivity<AddProductView> implements AddProductViewObserver, OnCategoriesLoaded
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
        view.updateLists(getSupportFragmentManager(), categories);
    }

    @Override
    public void onBack()
    {
        finish();
    }

    @Override
    public void onCreateProduct()
    {
        Intent intent = new Intent(this, CreateProductActivity.class);
        startActivity(intent);
    }

    @Override
    protected AddProductView view()
    {
        return new AddProductView(this);
    }
}