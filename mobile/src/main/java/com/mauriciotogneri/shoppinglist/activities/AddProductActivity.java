package com.mauriciotogneri.shoppinglist.activities;

import android.content.Intent;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.tasks.category.LoadCategories;
import com.mauriciotogneri.shoppinglist.tasks.category.LoadCategories.OnCategoriesLoaded;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.AddProductView;
import com.mauriciotogneri.shoppinglist.views.AddProductView.AddProductViewObserver;

import java.util.List;

public class AddProductActivity extends BaseActivity<AddProductView> implements AddProductViewObserver, OnCategoriesLoaded
{
    @Override
    public void onCategoriesLoaded(List<Category> categories)
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
    protected void onResume()
    {
        super.onResume();

        LoadCategories task = new LoadCategories(this, this);
        task.execute();
    }

    @Override
    protected AddProductView view()
    {
        return new AddProductView(this);
    }
}