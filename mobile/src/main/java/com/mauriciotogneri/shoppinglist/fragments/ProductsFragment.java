package com.mauriciotogneri.shoppinglist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.activities.CreateProductActivity;
import com.mauriciotogneri.shoppinglist.base.BaseFragment;
import com.mauriciotogneri.shoppinglist.database.UpdateProducts;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.utils.Analytics;
import com.mauriciotogneri.shoppinglist.views.Dialogs;
import com.mauriciotogneri.shoppinglist.views.ProductsListView;
import com.mauriciotogneri.shoppinglist.views.ProductsListView.ProductListViewObserver;

import java.util.Arrays;

public class ProductsFragment extends BaseFragment<ProductsListView> implements ProductListViewObserver
{
    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_PRODUCTS = "products";

    public static ProductsFragment create(String category, Product[] products)
    {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_CATEGORY, category);
        args.putSerializable(PARAM_PRODUCTS, products);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void initialize()
    {
        Product[] products = parameter(PARAM_PRODUCTS, new Product[0]);
        view.updateList(Arrays.asList(products));
    }

    public String title()
    {
        return parameter(PARAM_CATEGORY, "").toUpperCase();
    }

    @Override
    public void onProductSelected(Product product)
    {
        UpdateProducts updateProducts = new UpdateProducts(getContext());
        updateProducts.moveToCart(product);

        view.removeProduct(product);

        Analytics analytics = new Analytics(getContext());
        analytics.cartItemAdded(product);
    }

    @Override
    public void onProductsOptions(Product product)
    {
        String[] options = new String[2];
        options[0] = getString(R.string.button_edit);
        options[1] = getString(R.string.button_remove);

        Dialogs dialogs = new Dialogs(getContext());
        dialogs.options(product.name(), options, option -> {
            if (option == 0)
            {
                editProduct(product);
            }
            else if (option == 1)
            {
                confirmRemoveProduct(product);
            }
        });
    }

    private void editProduct(Product product)
    {
        Intent intent = CreateProductActivity.intent(getContext(), product);
        startActivity(intent);
    }

    private void confirmRemoveProduct(Product product)
    {
        Dialogs dialogs = new Dialogs(getContext());
        dialogs.confirmation(product.name(), getString(R.string.confirmation_remove_product), () -> removeProduct(product));
    }

    private void removeProduct(Product product)
    {
        Toast.makeText(getContext(), "DELETE: " + product.name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected ProductsListView view()
    {
        return new ProductsListView(getContext(), this);
    }
}