package com.mauriciotogneri.shoppinglist.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.tasks.LoadImages;
import com.mauriciotogneri.shoppinglist.tasks.LoadImages.OnImagesLoaded;
import com.mauriciotogneri.shoppinglist.views.SearchImageView;
import com.mauriciotogneri.shoppinglist.views.SearchImageView.SearchImageViewObserver;

import java.util.List;

public class SearchImageActivity extends BaseActivity<SearchImageView> implements SearchImageViewObserver, OnImagesLoaded
{
    public static final String PARAM_INITIAL_QUERY = "initial.query";
    public static final String PARAM_IMAGE_URL = "image.url";

    public static Intent intent(Context context, String initialQuery)
    {
        Intent intent = new Intent(context, SearchImageActivity.class);
        intent.putExtra(PARAM_INITIAL_QUERY, initialQuery);

        return intent;
    }

    @Override
    protected void initialize()
    {
        String initialQuery = parameter(PARAM_INITIAL_QUERY, "");

        if (!TextUtils.isEmpty(initialQuery))
        {
            view.query(initialQuery);
        }
    }

    @Override
    public void onImagesLoaded(List<String> images)
    {
        view.loadImages(images);
    }

    @Override
    public void onClose()
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
        Intent intent = new Intent();
        intent.putExtra(PARAM_IMAGE_URL, url);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected SearchImageView view()
    {
        return new SearchImageView(this);
    }
}