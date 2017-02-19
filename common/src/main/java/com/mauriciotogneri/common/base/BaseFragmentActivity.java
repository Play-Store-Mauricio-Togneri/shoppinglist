package com.mauriciotogneri.common.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseFragmentActivity extends FragmentActivity
{
    public abstract void addFragment(BaseFragment<?> fragment);

    public abstract void removeFragment();

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        // no call for super(). Bug on API Level > 11.
    }
}