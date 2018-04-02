package com.mauriciotogneri.shoppinglist.activities;

import android.content.Intent;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.database.LoadProductsByCategory;
import com.mauriciotogneri.shoppinglist.views.AddProductView;
import com.mauriciotogneri.shoppinglist.views.AddProductView.AddProductViewObserver;

public class AddProductActivity extends BaseActivity<AddProductView> implements AddProductViewObserver
{
    @Override
    protected void initialize()
    {
        LoadProductsByCategory loader = new LoadProductsByCategory(this, products -> view.updateLists(getSupportFragmentManager(), products));
        loader.execute();
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