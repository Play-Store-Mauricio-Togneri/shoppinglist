package com.mauriciotogneri.common.base;

import android.support.annotation.IdRes;
import android.view.View;

public class BaseUiContainer
{
    private final BaseView<?> baseView;

    public BaseUiContainer(BaseView baseView)
    {
        this.baseView = baseView;
    }

    protected View findViewById(@IdRes int viewId)
    {
        return baseView.findViewById(viewId);
    }
}