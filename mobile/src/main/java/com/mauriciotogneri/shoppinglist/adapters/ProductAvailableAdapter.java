package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductAvailableAdapter.ViewHolder;
import com.mauriciotogneri.shoppinglist.base.BaseListAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseViewHolder;
import com.mauriciotogneri.shoppinglist.model.Product;

public class ProductAvailableAdapter extends BaseListAdapter<Product, ViewHolder>
{
    public ProductAvailableAdapter(Context context)
    {
        super(context, R.layout.item_product_available);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Product product, int position)
    {
        viewHolder.name.setText(product.name());

        Glide.with(getContext())
                .load(product.image())
                .into(viewHolder.image);

        viewHolder.options.setOnClickListener(v-> Toast.makeText(getContext(), product.name(), Toast.LENGTH_SHORT).show());
    }

    @Override
    protected ViewHolder viewHolder(View view)
    {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends BaseViewHolder
    {
        @BindView(R.id.product_image)
        public ImageView image;

        @BindView(R.id.product_name)
        public TextView name;

        @BindView(R.id.product_options)
        public View options;

        protected ViewHolder(View view)
        {
            super(view);
        }
    }
}