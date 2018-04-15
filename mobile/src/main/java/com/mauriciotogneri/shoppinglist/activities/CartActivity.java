package com.mauriciotogneri.shoppinglist.activities;

import android.content.Intent;
import android.text.TextUtils;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.tasks.migration.Migration;
import com.mauriciotogneri.shoppinglist.tasks.migration.Migration.OnMigrationDone;
import com.mauriciotogneri.shoppinglist.tasks.product.LoadProductsInCart;
import com.mauriciotogneri.shoppinglist.tasks.product.LoadProductsInCart.OnProductsLoaded;
import com.mauriciotogneri.shoppinglist.tasks.product.UpdateProducts;
import com.mauriciotogneri.shoppinglist.utils.Analytics;
import com.mauriciotogneri.shoppinglist.utils.NormalPreferences;
import com.mauriciotogneri.shoppinglist.utils.WearableService;
import com.mauriciotogneri.shoppinglist.views.CartView;
import com.mauriciotogneri.shoppinglist.views.CartView.CartViewObserver;

import java.util.List;

public class CartActivity extends BaseActivity<CartView> implements CartViewObserver, OnProductsLoaded, OnMigrationDone
{
    @Override
    protected void initialize()
    {
        Analytics analytics = new Analytics(this);
        analytics.appLaunched();

        Intent intent = new Intent(this, WearableService.class);
        startService(intent);
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

        UpdateProducts task = new UpdateProducts(this);
        task.setSelection(product);
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

    private void reloadProducts()
    {
        LoadProductsInCart task = new LoadProductsInCart(this, this);
        task.execute();
    }

    @Override
    public void onMigrationDone()
    {
        NormalPreferences preferences = new NormalPreferences(this);
        preferences.migrationDone();

        reloadProducts();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        NormalPreferences preferences = new NormalPreferences(this);

        if (preferences.isMigrationDone())
        {
            reloadProducts();
        }
        else
        {
            Migration migration = new Migration(this, this);
            migration.execute();
        }
    }

    @Override
    protected void onDestroy()
    {
        UpdateProducts task = new UpdateProducts(this);
        task.removeFromCart(view.selectedProducts());

        super.onDestroy();
    }
}