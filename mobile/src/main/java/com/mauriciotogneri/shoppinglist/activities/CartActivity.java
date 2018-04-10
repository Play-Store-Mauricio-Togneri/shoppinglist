package com.mauriciotogneri.shoppinglist.activities;

import android.content.Intent;
import android.text.TextUtils;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.tasks.LoadProductsInCart;
import com.mauriciotogneri.shoppinglist.tasks.LoadProductsInCart.OnProductsLoaded;
import com.mauriciotogneri.shoppinglist.tasks.UpdateProducts;
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
    public void onShare(List<Product> products)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, shareContent(products));
        intent.setType("text/plain");

        startActivity(intent);
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

    private String shareContent(List<Product> products)
    {
        StringBuilder result = new StringBuilder();

        String lastCategory = "";

        for (Product product : products)
        {
            if (!product.isSelected())
            {
                if (!TextUtils.equals(product.category(), lastCategory))
                {
                    lastCategory = product.category();

                    if (result.length() != 0)
                    {
                        result.append("\n");
                    }

                    result.append(lastCategory).append(":\n");
                }

                result.append("   - ").append(product.name()).append("\n");
            }
        }

        return result.toString();
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