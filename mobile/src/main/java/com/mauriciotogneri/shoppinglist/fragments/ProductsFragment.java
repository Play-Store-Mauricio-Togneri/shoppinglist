package com.mauriciotogneri.shoppinglist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mauriciotogneri.shoppinglist.R;

public class ProductsFragment extends Fragment
{
    private String title;

    public static ProductsFragment create()
    {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString("title", "FOO");
        fragment.setArguments(args);

        return fragment;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.screen_category_products, container, false);

        return view;
    }
}