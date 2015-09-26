package com.mauriciotogneri.shoppinglist.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BaseAdapter<T, H extends BaseViewHolder<T>> extends WearableListView.Adapter
{
    private final List<T> items;
    private final LayoutInflater inflater;

    public BaseAdapter(Context context)
    {
        this.items = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    protected abstract void fill(T item, H viewHolder);

    protected abstract H getViewHolder(View view);

    protected abstract int getLayoutId();

    public void setData(List<T> list)
    {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void sort(Comparator<T> comparator)
    {
        Collections.sort(items, comparator);
        notifyDataSetChanged();
    }

    @Override
    @SuppressLint("InflateParams")
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return getViewHolder(inflater.inflate(getLayoutId(), null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, final int position)
    {
        T item = items.get(position);
        viewHolder.itemView.setTag(item);

        fill(item, (H) viewHolder);
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}