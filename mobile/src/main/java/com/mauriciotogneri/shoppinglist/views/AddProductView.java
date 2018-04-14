package com.mauriciotogneri.shoppinglist.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductsFragmentAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.fragments.ProductsFragment;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.AddProductView.AddProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.AddProductView.ViewContainer;

import java.util.ArrayList;
import java.util.List;

public class AddProductView extends BaseView<AddProductViewObserver, ViewContainer>
{
    private String lastCategorySelected;

    public AddProductView(AddProductViewObserver observer)
    {
        super(R.layout.screen_add_product, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        toolbarTitle(R.string.toolbar_title_add_product);
        enableBack(v -> observer.onBack());

        ui.pagerHeader.setDrawFullUnderline(false);
        ui.pagerHeader.setTabIndicatorColor(color(R.color.primary));
    }

    public void updateLists(FragmentManager fragmentManager, List<Category> categories)
    {
        List<ProductsFragment> fragments = new ArrayList<>();

        for (Category category : categories)
        {
            ProductsFragment fragment = ProductsFragment.create(category.name());
            fragments.add(fragment);
        }

        ui.pager.setOffscreenPageLimit(fragments.size());
        ProductsFragmentAdapter adapter = new ProductsFragmentAdapter(fragmentManager, fragments);
        ui.pager.setAdapter(adapter);

        if (!TextUtils.isEmpty(lastCategorySelected))
        {
            int position = categories.indexOf(new Category(lastCategorySelected));
            ui.pager.setCurrentItem(position);
        }
    }

    @OnClick(R.id.product_create)
    public void onActionButton()
    {
        ProductsFragmentAdapter adapter = (ProductsFragmentAdapter) ui.pager.getAdapter();
        ProductsFragment fragment = (ProductsFragment) adapter.getItem(ui.pager.getCurrentItem());
        lastCategorySelected = fragment.title();

        observer.onCreateProduct(fragment.title());
    }

    public interface AddProductViewObserver
    {
        void onBack();

        void onCreateProduct(String category);
    }

    public static class ViewContainer
    {
        @BindView(R.id.pager)
        public ViewPager pager;

        @BindView(R.id.pager_header)
        public PagerTabStrip pagerHeader;
    }
}