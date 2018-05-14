package com.mauriciotogneri.shoppinglist.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mauriciotogneri.shoppinglist.fragments.ProductsFragment;

import java.util.List;

public class ProductsFragmentAdapter extends FragmentPagerAdapter
{
    private final List<ProductsFragment> fragments;

    public ProductsFragmentAdapter(FragmentManager fragmentManager, List<ProductsFragment> fragments)
    {
        super(fragmentManager);

        this.fragments = fragments;
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return fragments.get(position).title().toUpperCase();
    }
}