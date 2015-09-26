package com.mauriciotogneri.shoppinglist.fragments;

import android.os.Bundle;

import com.mauriciotogneri.common.base.BaseFragment;
import com.mauriciotogneri.shoppinglist.dao.CartItemDao;
import com.mauriciotogneri.shoppinglist.dao.ProductDao;
import com.mauriciotogneri.shoppinglist.model.CartItem;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.productslist.ProductsListView;
import com.mauriciotogneri.shoppinglist.views.productslist.ProductsListViewInterface;
import com.mauriciotogneri.shoppinglist.views.productslist.ProductsListViewObserver;

import java.util.List;

public class ProductListFragment extends BaseFragment<ProductsListViewInterface> implements ProductsListViewObserver
{
    private static final String PARAMETER_CATEGORY = "category";

    public static ProductListFragment getInstance(Category category)
    {
        ProductListFragment fragment = new ProductListFragment();
        Bundle parameters = new Bundle();
        parameters.putSerializable(PARAMETER_CATEGORY, category);
        fragment.setArguments(parameters);

        return fragment;
    }

    @Override
    protected void initialize()
    {
        view.initialize(getContext(), this);
    }

    @Override
    public void onAddProduct(Product product)
    {
        CartItem cartItem = new CartItem(product, false);
        cartItem.save();

        view.removeProduct(product);
    }

    @Override
    public void onEditProduct(Product product)
    {
        UpdateProductFragment fragment = UpdateProductFragment.getInstance(product.getId());
        startFragment(fragment);
    }

    @Override
    public void onRemoveProduct(Product product)
    {
        CartItemDao cartItemDao = new CartItemDao();

        if (!cartItemDao.exists(product))
        {
            product.delete();

            view.removeProduct(product);
        }
        else
        {
            view.showError();
        }
    }

    @Override
    public void onActivate()
    {
        refreshList();
    }

    private void refreshList()
    {
        Category category = getCategory();

        if (category != null)
        {
            ProductDao productDao = new ProductDao();
            List<Product> list = productDao.getProducts(category);

            view.fillList(list);
        }
    }

    public Category getCategory()
    {
        return getParameter(PARAMETER_CATEGORY, null);
    }

    @Override
    protected ProductsListViewInterface getViewInstance()
    {
        return new ProductsListView();
    }
}