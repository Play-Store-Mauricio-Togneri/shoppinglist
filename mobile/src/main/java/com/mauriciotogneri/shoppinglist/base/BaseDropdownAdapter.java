package com.mauriciotogneri.shoppinglist.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDropdownAdapter<T, V> extends BaseListAdapter<T, V>
{
    private final int resourceIdOpen;

    public BaseDropdownAdapter(Context context, int resourceIdOpen, int resourceIdClose, List<T> list)
    {
        super(context, resourceIdClose, list);

        this.resourceIdOpen = resourceIdOpen;
    }

    public BaseDropdownAdapter(Context context, int resourceIdOpen, int resourceIdClose)
    {
        this(context, resourceIdOpen, resourceIdClose, new ArrayList<>());
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View rowView = inflater.inflate(resourceIdOpen, parent, false);
        V viewHolder = viewHolder(rowView);
        T item = getItem(position);

        fillView(viewHolder, item, position);

        return rowView;
    }
}