package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CartItemAdapter.ViewHolder;
import com.mauriciotogneri.shoppinglist.base.BaseListAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseListViewHolder;
import com.mauriciotogneri.shoppinglist.model.Product;

public class CartItemAdapter extends BaseListAdapter<Product, ViewHolder>
{
    public CartItemAdapter(Context context)
    {
        super(context, R.layout.item_cart_item);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Product item, int position)
    {
        viewHolder.name.setText(item.name());

        Glide.with(getContext())
                .load(item.image())
                .into(viewHolder.image);
    }

    @Override
    protected ViewHolder viewHolder(View view)
    {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends BaseListViewHolder
    {
        @BindView(R.id.product_image)
        public ImageView image;

        @BindView(R.id.product_name)
        public TextView name;

        protected ViewHolder(View view)
        {
            super(view);
        }
    }
}