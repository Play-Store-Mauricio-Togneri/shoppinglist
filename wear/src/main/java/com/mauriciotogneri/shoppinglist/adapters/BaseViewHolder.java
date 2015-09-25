package com.mauriciotogneri.shoppinglist.adapters;

import android.support.wearable.view.WearableListView;
import android.view.View;

public class BaseViewHolder<T> extends WearableListView.ViewHolder
{
    public BaseViewHolder(View itemView)
    {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public T get()
    {
        return (T) itemView.getTag();
    }
}
