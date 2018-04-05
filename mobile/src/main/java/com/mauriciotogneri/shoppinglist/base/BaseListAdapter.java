package com.mauriciotogneri.shoppinglist.base;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T, V> extends ArrayAdapter<T>
{
    protected final LayoutInflater inflater;
    private final int resourceId;

    public BaseListAdapter(Context context, int resourceId, List<T> list)
    {
        super(context, resourceId, list);

        this.inflater = LayoutInflater.from(getContext());
        this.resourceId = resourceId;
    }

    public BaseListAdapter(Context context, int resourceId)
    {
        this(context, resourceId, new ArrayList<>());
    }

    public void set(List<T> list)
    {
        clear();
        add(list);
        update();
    }

    public void add(List<T> list)
    {
        addAll(list);
        update();
    }

    public void update()
    {
        notifyDataSetChanged();
    }

    protected int color(@ColorRes int colorId)
    {
        return ContextCompat.getColor(getContext(), colorId);
    }

    protected abstract void fillView(V viewHolder, T item, int position);

    protected abstract V viewHolder(View view);

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent)
    {
        V viewHolder;

        View rowView = convertView;

        if (rowView == null)
        {
            rowView = inflater.inflate(resourceId, parent, false);

            viewHolder = viewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (V) rowView.getTag();
        }

        T item = getItem(position);

        fillView(viewHolder, item, position);

        return rowView;
    }
}