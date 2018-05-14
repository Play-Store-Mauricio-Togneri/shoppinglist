package com.mauriciotogneri.shoppinglist.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mauriciotogneri.androidutils.FragmentParameters;

public abstract class BaseFragment<V extends BaseView> extends Fragment
{
    protected V view;

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return setupView(inflater, container);
    }

    private View setupView(LayoutInflater inflater, ViewGroup container)
    {
        view = view();

        return view.inflate(inflater, container);
    }

    @Override
    public final void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    protected abstract V view();

    protected void initialize()
    {
    }

    @SuppressWarnings("unchecked")
    protected <A> A parameter(String key, A defaultValue)
    {
        FragmentParameters parameters = new FragmentParameters(getArguments());

        return parameters.parameter(key, defaultValue);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        // no call for super(), bug on API Level > 11
    }
}