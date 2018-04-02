package com.mauriciotogneri.shoppinglist.views;

import android.content.Context;
import android.widget.ListView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductAvailableAdapter;
import com.mauriciotogneri.shoppinglist.adapters.ProductAvailableAdapter.OnOptionsSelected;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.ProductsListView.ProductListViewObserver;
import com.mauriciotogneri.shoppinglist.views.ProductsListView.ViewContainer;

import java.util.List;

public class ProductsListView extends BaseView<ProductListViewObserver, ViewContainer> implements OnOptionsSelected
{
    private final ProductAvailableAdapter adapter;

    public ProductsListView(Context context, ProductListViewObserver observer)
    {
        super(R.layout.view_category_products, observer, new ViewContainer());

        this.adapter = new ProductAvailableAdapter(context, this);
    }

    public void updateList(List<Product> products)
    {
        adapter.set(products);

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener((adapterView, view, position, id) -> {
            Product product = (Product) adapterView.getItemAtPosition(position);
            observer.onProductSelected(product);
        });
    }

    public void removeProduct(Product product)
    {
        adapter.remove(product);
    }

    @Override
    public void onOptionsSelected(Product product)
    {
        observer.onProductsOptions(product);
    }

    public interface ProductListViewObserver
    {
        void onProductSelected(Product product);

        void onProductsOptions(Product product);
    }

    public static class ViewContainer
    {
        @BindView(R.id.product_list)
        public ListView list;
    }
}