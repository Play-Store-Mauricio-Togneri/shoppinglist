package com.mauriciotogneri.shoppinglist.views;

import android.widget.ListView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CartItemAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.CartView.CartViewObserver;
import com.mauriciotogneri.shoppinglist.views.CartView.ViewContainer;

import java.util.List;

public class CartView extends BaseView<CartViewObserver, ViewContainer>
{
    private CartItemAdapter adapter;

    public CartView(CartViewObserver observer)
    {
        super(R.layout.screen_main, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        toolbarTitle(R.string.application_name);

        adapter = new CartItemAdapter(context());
        ui.list.setAdapter(adapter);

        ui.list.setOnItemClickListener((adapterView, view, position, id) -> {
            Product product = (Product) adapterView.getItemAtPosition(position);
            observer.onProduceSelected(product);
        });
    }

    public void updateList(List<Product> products)
    {
        adapter.set(products);

        if (products.isEmpty())
        {
            disableToolbarAction();
        }
        else
        {
            enableToolbarAction(R.drawable.ic_share, v -> observer.onShare());
        }
    }

    public void updateList()
    {
        adapter.update();
    }

    public interface CartViewObserver
    {
        void onProduceSelected(Product product);

        void onShare();

        void onAddProduct();
    }

    public static class ViewContainer
    {
        @BindView(R.id.product_list)
        public ListView list;
    }
}