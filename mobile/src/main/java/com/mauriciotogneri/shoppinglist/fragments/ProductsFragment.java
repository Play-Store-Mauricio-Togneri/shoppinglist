package com.mauriciotogneri.shoppinglist.fragments;

import android.os.Bundle;

import com.mauriciotogneri.shoppinglist.base.BaseFragment;
import com.mauriciotogneri.shoppinglist.database.UpdateProducts;
import com.mauriciotogneri.shoppinglist.model.Product;
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
    }

    @Override
    protected ProductsListView view()
    {
        return new ProductsListView(getContext(), this);
    }
}