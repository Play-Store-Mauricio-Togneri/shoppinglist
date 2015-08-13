package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ListCategoryAdapter.ViewHolder;
import com.mauriciotogneri.shoppinglist.model.Category;

import java.util.List;

public class ListCategoryAdapter extends BaseListAdapter<Category, ViewHolder>
{
    public ListCategoryAdapter(Context context)
    {
        super(context, R.layout.row_category);
    }

    @Override
    protected ViewHolder getViewHolder(View view)
    {
        return new ViewHolder(view);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Category category, int position)
    {
        viewHolder.name.setText(category.getName());
        viewHolder.color.setBackgroundColor(category.getIntColor());
    }

    public void refresh(List<Category> list)
    {
        clear();
        addAll(list);
        notifyDataSetChanged();
    }

    protected static class ViewHolder
    {
        public final TextView name;
        public final TextView color;

        public ViewHolder(View view)
        {
            this.name = (TextView) view.findViewById(R.id.name);
            this.color = (TextView) view.findViewById(R.id.color);
        }
    }
}