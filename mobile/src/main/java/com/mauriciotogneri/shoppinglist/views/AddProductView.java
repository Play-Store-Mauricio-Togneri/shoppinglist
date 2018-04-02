package com.mauriciotogneri.shoppinglist.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductsFragmentAdapter;
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
        toolbarTitle(R.string.toolbar_title_add_product);
        enableBack(v -> observer.onBack());

        ui.buttonCreate.setOnClickListener(v -> observer.onCreateProduct());

        ui.pagerHeader.setDrawFullUnderline(false);
        ui.pagerHeader.setTabIndicatorColor(color(R.color.primary));
    }

    public void updateLists(FragmentManager fragmentManager)
    {
        ui.pager.setOffscreenPageLimit(10); // TODO
        ProductsFragmentAdapter adapter = new ProductsFragmentAdapter(fragmentManager);
        ui.pager.setAdapter(adapter);
    }

    public interface AddProductViewObserver
    {
        void onBack();

        void onProduceSelected(Product product);

        void onCreateProduct();
    }

    public static class ViewContainer
    {
        @BindView(R.id.product_create)
        public View buttonCreate;

        @BindView(R.id.pager)
        public ViewPager pager;

        @BindView(R.id.pager_header)
        public PagerTabStrip pagerHeader;
    }
}