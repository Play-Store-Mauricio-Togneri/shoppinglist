package com.mauriciotogneri.shoppinglist.views.cart;

import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;

import java.util.List;

public interface CartViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(CartViewObserver observer);

    void displayData(List<CartElement> list);

    void onAmbientMode(boolean onAmbientMode);

    void markCartElement(CartElement cartElement);
}