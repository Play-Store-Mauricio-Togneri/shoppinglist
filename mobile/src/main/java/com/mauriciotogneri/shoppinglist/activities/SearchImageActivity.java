package com.mauriciotogneri.shoppinglist.activities;

import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.database.LoadImages;
import com.mauriciotogneri.shoppinglist.database.LoadImages.OnImagesLoaded;
import com.mauriciotogneri.shoppinglist.views.SearchImageView;
import com.mauriciotogneri.shoppinglist.views.SearchImageView.SearchImageViewObserver;

import java.util.List;

public class SearchImageActivity extends BaseActivity<SearchImageView> implements SearchImageViewObserver, OnImagesLoaded
{
    @Override
    protected void initialize()
    {
    }

    @Override
    public void onImagesLoaded(List<String> images)
    {
        view.loadImages(images);
    }

    @Override
    public void onBack()
    {
        finish();
    }

    @Override
    public void onSearch(String query)
    {
        view.loadingMode();

        LoadImages loadImages = new LoadImages(this);
        loadImages.execute(query);
    }

    @Override
    public void onImageSelected(String url)
    {
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected SearchImageView view()
    {
        return new SearchImageView(this);
    }
}