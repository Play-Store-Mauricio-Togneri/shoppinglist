package com.mauriciotogneri.shoppinglist.views;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductInCartAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.CartView.CartViewObserver;
import com.mauriciotogneri.shoppinglist.views.CartView.ViewContainer;

import java.util.ArrayList;
import java.util.List;

public class CartView extends BaseView<CartViewObserver, ViewContainer>
{
    private final ProductInCartAdapter adapter;

    public CartView(Context context, CartViewObserver observer)
    {
        super(R.layout.screen_cart, observer, new ViewContainer());

        this.adapter = new ProductInCartAdapter(context);
    }

    @Override
    protected void initialize()
    {
        toolbarTitle(R.string.toolbar_title_main);

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener((adapterView, view, position, id) -> {
            Product product = (Product) adapterView.getItemAtPosition(position);
            observer.onProductSelected(product);
        });
    }

    public void updateList(List<Product> products)
    {
        if (products.isEmpty())
        {
            disableToolbarAction();
            ui.labelEmpty.setVisibility(View.VISIBLE);
            ui.list.setVisibility(View.GONE);
        }
        else
        {
            ui.labelEmpty.setVisibility(View.GONE);
            ui.list.setVisibility(View.VISIBLE);
            adapter.set(products);
            enableToolbarAction(R.drawable.ic_share, v -> observer.onShare());
        }
    }

    public List<Product> selectedProducts()
    {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < adapter.getCount(); i++)
        {
            Product product = adapter.getItem(i);

            if ((product != null) && product.isSelected())
            {
                products.add(product);
            }
        }

        return products;
    }

    public void updateList()
    {
        adapter.update();
    }

    @OnClick(R.id.product_add)
    public void onAddProduct()
    {
        observer.onAddProduct();
    }

    public interface CartViewObserver
    {
        void onProductSelected(Product product);

        void onShare();

        void onAddProduct();
    }

    public static class ViewContainer
    {
        @BindView(R.id.product_list)
        public ListView list;

        @BindView(R.id.label_empty)
        public View labelEmpty;
    }
}