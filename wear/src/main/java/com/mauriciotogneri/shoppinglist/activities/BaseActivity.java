package com.mauriciotogneri.shoppinglist.activities;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.view.ViewGroup;

import com.mauriciotogneri.common.base.BaseViewInterface;

public abstract class BaseActivity<V extends BaseViewInterface> extends WearableActivity
{
    protected V view;

    @Override
    public final void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setAmbientEnabled();

        this.view = getViewInstance();
        ViewGroup container = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        View layout = this.view.init(getLayoutInflater(), container);
        setContentView(layout);

        initialize();
    }

    protected abstract V getViewInstance();

    protected void initialize()
    {
    }
}