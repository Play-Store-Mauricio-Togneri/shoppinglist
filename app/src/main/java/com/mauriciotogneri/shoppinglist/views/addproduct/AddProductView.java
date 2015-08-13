package com.mauriciotogneri.shoppinglist.views.addproduct;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductPagerAdapter;
import com.mauriciotogneri.shoppinglist.fragments.ProductListFragment;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.BaseView;

import java.util.List;

public class AddProductView extends BaseView implements AddProductViewInterface
{
    private ProductPagerAdapter productPagerAdapter;
    private ViewPager viewPager;

    @Override
    public void initialize(final Context context, FragmentManager fragmentManager, List<Category> categoryList, final AddProductViewObserver observer)
    {
        this.productPagerAdapter = new ProductPagerAdapter(fragmentManager, categoryList);
        this.viewPager = getViewPager(R.id.pager);
        this.viewPager.setAdapter(productPagerAdapter);

        setButtonAction(R.id.create_product, new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onCreateProduct();
            }
        });
    }

    @Override
    public Category getSelectedCategory()
    {
        ProductListFragment productListFragment = (ProductListFragment) productPagerAdapter.getItem(viewPager.getCurrentItem());

        return productListFragment.getCategory();
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_add_product;
    }
}