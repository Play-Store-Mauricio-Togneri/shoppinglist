package com.mauriciotogneri.shoppinglist.views.dialogs;

import android.content.Context;

import com.mauriciotogneri.common.base.BaseDialog;

public class DialogMenu extends BaseDialog
{
    public DialogMenu(Context context, String title)
    {
        super(context, title);
    }

    @Override
    protected int getViewId()
    {
        return 0;
    }
}