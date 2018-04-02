package com.mauriciotogneri.shoppinglist.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mauriciotogneri.shoppinglist.fragments.ProductsFragment;

public class ProductsFragmentAdapter extends FragmentPagerAdapter
{
    public ProductsFragmentAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public int getCount()
    {
        return 10;
    }

    @Override
    public Fragment getItem(int position)
    {
        /*switch (position)
        {
            case 0:
                return ProductsFragment.create("Page # 1");

            case 1:
                return ProductsFragment.create("Page # 2");

            case 2:
                return ProductsFragment.create("Page # 3");

            default:
                return null;
        }*/

        return ProductsFragment.create();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return "PAGE " + position;
    }
}