package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CategoryAdapter.ViewHolder;
import com.mauriciotogneri.shoppinglist.base.BaseDropdownAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseViewHolder;

public class CategoryAdapter extends BaseDropdownAdapter<String, ViewHolder>
{
    public CategoryAdapter(Context context)
    {
        super(context, R.layout.item_category_open, R.layout.item_category_close);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, String category, int position)
    {
        viewHolder.name.setText(category);
    }

    @Override
    protected ViewHolder viewHolder(View view)
    {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends BaseViewHolder
    {
        @BindView(R.id.category_name)
        public TextView name;

        protected ViewHolder(View view)
        {
            super(view);
        }
    }
}