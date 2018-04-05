package com.mauriciotogneri.shoppinglist.activities;

import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.views.SearchImageView;
import com.mauriciotogneri.shoppinglist.views.SearchImageView.SearchImageViewObserver;

public class SearchImageActivity extends BaseActivity<SearchImageView> implements SearchImageViewObserver
{
    @Override
    protected void initialize()
    {
        // TODO
    }

    @Override
    public void onBack()
    {
        finish();
    }

    @Override
    public void onImageSelected(String url)
    {
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected SearchImageView view()
    {
        return new SearchImageView(this);
    }
}