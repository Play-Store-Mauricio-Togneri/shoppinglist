package com.mauriciotogneri.shoppinglist.base;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.mauriciotogneri.androidutils.ActivityParameters;
import com.mauriciotogneri.androidutils.ToastMessage;

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

    @SuppressWarnings("unchecked")
    protected <A> A parameter(String key, A defaultValue)
    {
        ActivityParameters parameters = new ActivityParameters(getIntent());

        return parameters.parameter(key, defaultValue);
    }

    protected abstract V view();

    protected void initialize()
    {
    }

    protected void toast(@StringRes int resId)
    {
        new ToastMessage(this).shortMessage(resId);
    }
}