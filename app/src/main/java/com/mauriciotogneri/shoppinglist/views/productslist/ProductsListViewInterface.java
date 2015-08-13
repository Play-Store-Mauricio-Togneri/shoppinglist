package com.mauriciotogneri.shoppinglist.views.productslist;

import android.content.Context;

import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.BaseViewInterface;

import java.util.List;

public interface ProductsListViewInterface extends BaseViewInterface
{
    void initialize(Context context, ProductsListViewObserver observer);

    void showError(Context context);

    void refreshList(List<Product> list);
}