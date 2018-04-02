package com.mauriciotogneri.shoppinglist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mauriciotogneri.shoppinglist.R;

public class ProductsFragment extends Fragment
{
    public static ProductsFragment create(String title)
    {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);

        return fragment;
    }

    public String title()
    {
        return getArguments().getString("title").toUpperCase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.screen_category_products, container, false);

        return view;
    }
}