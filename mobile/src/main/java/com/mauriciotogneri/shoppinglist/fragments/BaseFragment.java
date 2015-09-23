package com.mauriciotogneri.shoppinglist.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mauriciotogneri.common.base.BaseViewInterface;
import com.mauriciotogneri.shoppinglist.activities.MainActivity;

public abstract class BaseFragment<V extends BaseViewInterface> extends Fragment
{
    protected MainActivity activity;
    protected V view;
    private Object result = null;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = getViewInstance();

        return view.init(inflater, container);
    }

    @Override
    public final void onDestroyView()
    {
        onFinish();
        view = null;

        super.onDestroyView();
    }

    protected abstract V getViewInstance();

    protected void onFinish()
    {
    }

    protected void initialize()
    {
    }

    public void onActivate()
    {
    }

    public void onActivate(@SuppressWarnings("unused") Object result)
    {
    }

    public Context getContext()
    {
        return activity;
    }

    @Override
    public final void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initialize();
        onActivate();
    }

    @Override
    public final void onAttach(Activity parent)
    {
        super.onAttach(parent);

        activity = (MainActivity) parent;
    }

    @SuppressWarnings("unchecked")
    protected <Type> Type getParameter(String key, Type defaultValue)
    {
        Bundle extras = getArguments();

        if ((extras != null) && extras.containsKey(key))
        {
            return (Type) extras.get(key);
        }
        else
        {
            return defaultValue;
        }
    }

    protected void startFragment(BaseFragment<?> fragment)
    {
        activity.addFragment(fragment);
    }

    protected void close()
    {
        activity.removeFragment();
    }

    protected void setResult(Object value)
    {
        result = value;
    }

    public Object getResult()
    {
        return result;
    }

    protected void share(int titleId, String content)
    {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(titleId)));
    }
}