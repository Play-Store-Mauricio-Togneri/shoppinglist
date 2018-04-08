package com.mauriciotogneri.shoppinglist.activities;

import android.content.Intent;
import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.database.LoadProductsInCart;
import com.mauriciotogneri.shoppinglist.database.LoadProductsInCart.OnProductsLoaded;
import com.mauriciotogneri.shoppinglist.database.UpdateProducts;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.utils.Analytics;
import com.mauriciotogneri.shoppinglist.views.CartView;
import com.mauriciotogneri.shoppinglist.views.CartView.CartViewObserver;

import java.util.List;

public class CartActivity extends BaseActivity<CartView> implements CartViewObserver, OnProductsLoaded
{
    @Override
    protected void initialize()
    {
        Analytics analytics = new Analytics(this);
        analytics.appLaunched();
    }

    @Override
    public void onProductsLoaded(List<Product> products)
    {
        view.updateList(products);
    }

    @Override
    public void onProductSelected(Product product)
    {
        product.toggleSelection();
        view.updateList();

        UpdateProducts updateProducts = new UpdateProducts(this);
        updateProducts.setSelection(product);
    }

    @Override
    public void onShare()
    {
        Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddProduct()
    {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    @Override
    protected CartView view()
    {
        return new CartView(this, this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        LoadProductsInCart loader = new LoadProductsInCart(this, this);
        loader.execute();
    }

    @Override
    protected void onDestroy()
    {
        UpdateProducts updateProducts = new UpdateProducts(this);
        updateProducts.removeFromCart(view.selectedProducts());

        super.onDestroy();
    }
}