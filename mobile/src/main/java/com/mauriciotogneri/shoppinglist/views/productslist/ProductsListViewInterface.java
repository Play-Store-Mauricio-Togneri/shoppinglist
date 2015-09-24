package com.mauriciotogneri.shoppinglist.views.productslist;

import android.content.Context;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;
import com.mauriciotogneri.shoppinglist.model.Product;

import java.util.List;

public interface ProductsListViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(Context context, ProductsListViewObserver observer);

    void showError();

    void fillList(List<Product> list);
}