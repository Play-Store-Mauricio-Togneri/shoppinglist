package com.mauriciotogneri.shoppinglist.views.cart;

import com.mauriciotogneri.common.api.CartElement;

public interface CartViewObserver
{
    void onStubReady();

    void onCartElementSelected(CartElement cartElement);
}