package com.mauriciotogneri.common.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface BaseViewInterface<UI extends BaseUiContainer>
{
    View init(LayoutInflater inflater, ViewGroup container);

    View findViewById(@IdRes int viewId);

    int getViewId();

    UI getUiContainer(BaseView baseView);

    Context getContext();

    void showToast(int messageId);
}