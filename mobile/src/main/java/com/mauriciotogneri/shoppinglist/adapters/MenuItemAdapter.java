package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.MenuItemAdapter.Option;
import com.mauriciotogneri.shoppinglist.adapters.MenuItemAdapter.ViewHolder;

import java.util.List;

public class MenuItemAdapter extends BaseListAdapter<Option, ViewHolder>
{
    public MenuItemAdapter(Context context, List<Option> options)
    {
        super(context, R.layout.row_menu_item, options);
    }

    @Override
    protected ViewHolder getViewHolder(View view)
    {
        return new ViewHolder(view);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Option option, int position)
    {
        viewHolder.icon.setText(option.icon);
        viewHolder.title.setText(option.title);
    }

    public static class Option
    {
        public final String icon;
        public final String title;

        public Option(String icon, String title)
        {
            this.icon = icon;
            this.title = title;
        }
    }

    protected static class ViewHolder
    {
        public final TextView icon;
        public final TextView title;

        public ViewHolder(View view)
        {
            this.icon = (TextView) view.findViewById(R.id.icon);
            this.title = (TextView) view.findViewById(R.id.title);
        }
    }
}