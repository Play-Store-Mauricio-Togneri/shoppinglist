package com.mauriciotogneri.shoppinglist.activities;

import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.AddProductView;
import com.mauriciotogneri.shoppinglist.views.AddProductView.AddProductViewObserver;

public class AddProductActivity extends BaseActivity<AddProductView> implements AddProductViewObserver
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
    public void onProduceSelected(Product product)
    {
        Toast.makeText(this, product.name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateProduct()
    {
        Toast.makeText(this, "CREATE", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected AddProductView view()
    {
        return new AddProductView(this);
    }
}