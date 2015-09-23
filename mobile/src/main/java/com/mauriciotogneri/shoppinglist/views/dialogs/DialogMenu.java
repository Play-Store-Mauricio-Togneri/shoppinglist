package com.mauriciotogneri.shoppinglist.views.dialogs;

import android.content.Context;

import com.mauriciotogneri.common.widgets.CustomDialog;

public class DialogMenu extends CustomDialog
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