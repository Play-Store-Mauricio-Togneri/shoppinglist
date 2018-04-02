package com.mauriciotogneri.shoppinglist.base;

import android.view.View;

import com.mauriciotogneri.androidutils.uibinder.UiBinder;

public class BaseViewHolder
{
    protected BaseViewHolder(View view)
    {
        UiBinder uiBinder = new UiBinder();
        uiBinder.bind(view, this);
    }
}