package com.mauriciotogneri.shoppinglist.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseActivity<V extends BaseView> extends AppCompatActivity
{
    protected V view;

    @Override
    public final void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        view = view();
        ViewGroup container = getWindow().getDecorView().findViewById(android.R.id.content);
        View layout = view.inflate(getLayoutInflater(), container);
        setContentView(layout);

        initialize();
    }

    protected abstract V view();

    protected void initialize()
    {
    }
}