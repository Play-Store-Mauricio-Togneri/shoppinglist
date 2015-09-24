package com.mauriciotogneri.common.base;

import android.support.v4.app.FragmentActivity;

public abstract class BaseFragmentActivity extends FragmentActivity
{
    public abstract void addFragment(BaseFragment<?> fragment);

    public abstract void removeFragment();
}