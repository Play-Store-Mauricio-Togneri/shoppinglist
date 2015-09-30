package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.widgets.CustomImageView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CartAdapter.CartViewHolder;

public class CartAdapter extends BaseAdapter<CartElement, CartViewHolder>
{
    private boolean onAmbientMode = false;

    public CartAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected synchronized void fill(CartElement cartElement, CartViewHolder viewHolder)
    {
        viewHolder.name.setText(cartElement.name);

        if (cartElement.isSelected)
        {
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() | (Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else
        {
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        if (onAmbientMode)
        {
            if (cartElement.isSelected)
            {
                viewHolder.name.setTextColor(getContext().getResources().getColor(R.color.default_text_color));
            }
            else
            {
                viewHolder.name.setTextColor(Color.WHITE);
            }

            viewHolder.thumbnail.setVisibility(View.INVISIBLE);
        }
        else
        {
            viewHolder.name.setTextColor(getContext().getResources().getColor(R.color.default_text_color));

            viewHolder.thumbnail.setVisibility(View.VISIBLE);
            viewHolder.thumbnail.setImage(cartElement.picture, cartElement.isSelected);
        }
    }

    @Override
    protected CartViewHolder getViewHolder(View view)
    {
        return new CartViewHolder(view);
    }

    public synchronized void setOnAmbientMode(boolean value)
    {
        onAmbientMode = value;
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.row_cart_item;
    }

    public static class CartViewHolder extends BaseViewHolder<CartElement>
    {
        public final CustomImageView thumbnail;
        public final TextView name;

        public CartViewHolder(View itemView)
        {
            super(itemView);

            this.thumbnail = (CustomImageView) itemView.findViewById(R.id.thumbnail);
            this.name = (TextView) itemView.findViewById(R.id.product_name);
        }
    }
}