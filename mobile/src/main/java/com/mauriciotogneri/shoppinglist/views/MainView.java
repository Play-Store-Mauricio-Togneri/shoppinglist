package com.mauriciotogneri.shoppinglist.views;

import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.views.MainView.MainViewObserver;
import com.mauriciotogneri.shoppinglist.views.MainView.ViewContainer;

public class MainView extends BaseView<MainViewObserver, ViewContainer>
{
    public MainView(MainViewObserver observer)
    {
        super(R.layout.screen_main, observer, new ViewContainer());
    }

    public interface MainViewObserver
    {
        void onAddProduct();
    }

    public static class ViewContainer
    {
        //@BindView(R.id.inputlayout_email)
        //public CustomInputLayout inputLayoutEmail;
    }
}