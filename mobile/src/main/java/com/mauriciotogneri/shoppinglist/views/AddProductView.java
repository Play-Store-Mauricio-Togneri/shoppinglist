package com.mauriciotogneri.shoppinglist.views;

import android.view.View;
import android.widget.ListView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.AddProductView.AddProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.AddProductView.ViewContainer;

public class AddProductView extends BaseView<AddProductViewObserver, ViewContainer>
{
    public AddProductView(AddProductViewObserver observer)
    {
        super(R.layout.screen_add_product, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        toolbarTitle(R.string.application_name);
        enableBack(v -> observer.onBack());
    }

    public interface AddProductViewObserver
    {
        void onBack();

        void onProduceSelected(Product product);

        void onCreateProduct();
    }

    public static class ViewContainer
    {
        @BindView(R.id.product_list)
        public ListView list;

        @BindView(R.id.product_add)
        public View buttonAdd;
    }
}