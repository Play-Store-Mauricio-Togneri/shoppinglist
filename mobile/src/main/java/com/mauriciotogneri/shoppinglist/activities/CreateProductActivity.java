package com.mauriciotogneri.shoppinglist.activities;

import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.views.CreateProductView;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.CreateProductViewObserver;

public class CreateProductActivity extends BaseActivity<CreateProductView> implements CreateProductViewObserver
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
    public void onCreate()
    {
        Toast.makeText(this, "CREATE", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected CreateProductView view()
    {
        return new CreateProductView(this);
    }
}