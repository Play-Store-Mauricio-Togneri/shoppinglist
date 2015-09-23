package com.mauriciotogneri.shoppinglist.views.addproduct;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductPagerAdapter;
import com.mauriciotogneri.shoppinglist.fragments.ProductListFragment;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.addproduct.AddProductView.UiContainer;

import java.util.List;

public class AddProductView extends BaseView<UiContainer> implements AddProductViewInterface<UiContainer>
{
    private ProductPagerAdapter productPagerAdapter;

    @Override
    public void initialize(final Context context, FragmentManager fragmentManager, List<Category> categoryList, int selected, final AddProductViewObserver observer)
    {
        productPagerAdapter = new ProductPagerAdapter(context, fragmentManager, categoryList);

        ui.pager.setAdapter(productPagerAdapter);
        ui.pager.setOffscreenPageLimit(categoryList.size());
        ui.pager.setCurrentItem(selected);

        ui.buttonCreateProduct.setOnClickListener(new OnClickListener()
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
        ProductListFragment productListFragment = (ProductListFragment) productPagerAdapter.getItem(ui.pager.getCurrentItem());

        return productListFragment.getCategory();
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_add_product;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private final ViewPager pager;
        private final TextView buttonCreateProduct;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.pager = (ViewPager) findViewById(R.id.pager);
            this.buttonCreateProduct = (TextView) findViewById(R.id.create_product);
        }
    }
}