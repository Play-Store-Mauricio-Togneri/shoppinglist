package com.mauriciotogneri.shoppinglist.views.cart;

import android.content.Context;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;
import com.mauriciotogneri.shoppinglist.model.CartItem;

import java.util.List;

public interface CartViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(Context context, CartViewObserver observer);

    void removeCartItem(CartItem cartItem);

    void removeSelectedItems();

    String getShareContent();

    void refreshList(List<CartItem> list);
}