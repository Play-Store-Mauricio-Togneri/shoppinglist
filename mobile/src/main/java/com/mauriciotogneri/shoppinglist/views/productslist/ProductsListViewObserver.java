package com.mauriciotogneri.shoppinglist.views.productslist;

import com.mauriciotogneri.shoppinglist.model.Product;

public interface ProductsListViewObserver
{
    void onAddProduct(Product product);

    void onEditProduct(Product product);

    void onRemoveProduct(Product product);
}
