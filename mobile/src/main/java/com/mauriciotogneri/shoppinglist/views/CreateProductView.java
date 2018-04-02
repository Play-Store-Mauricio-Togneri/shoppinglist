package com.mauriciotogneri.shoppinglist.views;

import android.view.View;
import android.widget.ListView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.CreateProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.ViewContainer;

public class CreateProductView extends BaseView<CreateProductViewObserver, ViewContainer>
{
    public CreateProductView(CreateProductViewObserver observer)
    {
        super(R.layout.screen_create_product, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        toolbarTitle(R.string.toolbar_title_create_product);
        enableBack(v -> observer.onBack());
    }

    public interface CreateProductViewObserver
    {
        void onBack();

        void onCreate();
    }

    public static class ViewContainer
    {
        @BindView(R.id.product_list)
        public ListView list;

        @BindView(R.id.product_add)
        public View buttonAdd;
    }
}