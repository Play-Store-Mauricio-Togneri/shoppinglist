package com.mauriciotogneri.shoppinglist.views;

import android.widget.ListView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CartItemAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.CartView.CartViewObserver;
import com.mauriciotogneri.shoppinglist.views.CartView.ViewContainer;

import java.util.ArrayList;
import java.util.List;

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

        List<Product> products = new ArrayList<>();
        products.add(new Product("Product 1", new Category(""), "", false));
        products.add(new Product("Product 2", new Category(""), "", false));
        products.add(new Product("Product 3", new Category(""), "", false));
        products.add(new Product("Product 4", new Category(""), "", false));
        products.add(new Product("Product 5", new Category(""), "", false));
        products.add(new Product("Product 6", new Category(""), "", false));
        products.add(new Product("Product 7", new Category(""), "", false));
        products.add(new Product("Product 8", new Category(""), "", false));
        products.add(new Product("Product 9", new Category(""), "", false));
        products.add(new Product("Product 10", new Category(""), "", false));

        CartItemAdapter adapter = new CartItemAdapter(context());
        adapter.add(products);
        ui.list.setAdapter(adapter);

        ui.list.setOnItemClickListener((adapterView, view, position, id) -> {
            Product product = (Product) adapterView.getItemAtPosition(position);
            observer.onProduceSelected(product);
        });
    }

    public interface CartViewObserver
    {
        void onProduceSelected(Product product);

        void onAddProduct();
    }

    public static class ViewContainer
    {
        @BindView(R.id.product_list)
        public ListView list;
    }
}