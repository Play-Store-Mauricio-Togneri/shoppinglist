package com.mauriciotogneri.shoppinglist.base;

import android.view.View;

import com.mauriciotogneri.androidutils.uibinder.UiBinder;

public class BaseListViewHolder
{
    protected BaseListViewHolder(View view)
    {
        UiBinder uiBinder = new UiBinder();
        uiBinder.bind(view, this);
    }
}