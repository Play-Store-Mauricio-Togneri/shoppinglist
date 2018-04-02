package com.mauriciotogneri.shoppinglist.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.R;
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
    public void onProductsOptions(Product product)
    {
        String[] options = new String[2];
        options[0] = getString(R.string.button_edit);
        options[1] = getString(R.string.button_remove);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        SpannableStringBuilder spannable = new SpannableStringBuilder(product.name());

        spannable.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.primary)),
                0,
                product.name().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setTitle(spannable);
        builder.setItems(options, (dialog, which) -> {
            if (which == 0)
            {
                editProduct(product);
            }
            else if (which == 1)
            {
                removeProduct(product);
            }
        });
        builder.show();
    }

    private void editProduct(Product product)
    {
        Toast.makeText(getContext(), "EDIT: " + product.name(), Toast.LENGTH_SHORT).show();
    }

    private void removeProduct(Product product)
    {
        Toast.makeText(getContext(), "REMOVE: " + product.name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected ProductsListView view()
    {
        return new ProductsListView(getContext(), this);
    }
}