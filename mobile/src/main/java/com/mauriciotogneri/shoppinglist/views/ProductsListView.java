package com.mauriciotogneri.shoppinglist.views;

import android.widget.ListView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.ProductsListView.ProductListViewObserver;
import com.mauriciotogneri.shoppinglist.views.ProductsListView.ViewContainer;

import java.util.List;

public class ProductsListView extends BaseView<ProductListViewObserver, ViewContainer>
{
    public ProductsListView(ProductListViewObserver observer)
    {
        super(R.layout.screen_category_products, observer, new ViewContainer());
    }

    public void updateList(List<Product> products)
    {
        ProductAdapter adapter = new ProductAdapter(context());
        adapter.set(products);

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener((adapterView, view, position, id) -> {
            Product product = (Product) adapterView.getItemAtPosition(position);
            observer.onProduceSelected(product);
        });
    }

    public interface ProductListViewObserver
    {
        void onProduceSelected(Product product);
    }

    public static class ViewContainer
    {
        @BindView(R.id.product_list)
        public ListView list;
    }
}