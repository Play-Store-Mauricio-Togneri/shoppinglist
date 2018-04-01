package com.mauriciotogneri.shoppinglist.views;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.views.CartView.CartViewObserver;
import com.mauriciotogneri.shoppinglist.views.CartView.ViewContainer;

public class CartView extends BaseView<CartViewObserver, ViewContainer>
{
    public CartView(CartViewObserver observer)
    {
        super(R.layout.screen_main, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        title(R.string.application_name);
    }

    public interface CartViewObserver
    {
        void onAddProduct();
    }

    public static class ViewContainer
    {
        //@BindView(R.id.inputlayout_email)
        //public CustomInputLayout inputLayoutEmail;
    }
}