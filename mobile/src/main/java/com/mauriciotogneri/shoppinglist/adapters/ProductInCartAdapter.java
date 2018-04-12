package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ProductInCartAdapter.ViewHolder;
import com.mauriciotogneri.shoppinglist.base.BaseListAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseViewHolder;
import com.mauriciotogneri.shoppinglist.model.Product;

public class ProductInCartAdapter extends BaseListAdapter<Product, ViewHolder>
{
    private final RequestManager imageLoader;

    public ProductInCartAdapter(Context context)
    {
        super(context, R.layout.item_product_in_cart);

        this.imageLoader = Glide.with(context);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Product product, int position)
    {
        if (product.isSelected())
        {
            viewHolder.row.setBackgroundColor(color(R.color.item_selected));
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.check.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.row.setBackground(null);
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.check.setVisibility(View.GONE);
        }

        viewHolder.name.setText(product.name());

        imageLoader.load(product.image()).into(viewHolder.image);
    }

    public void sortList()
    {
        sort((p1, p2) -> {
            if (!p1.isSelected() && p2.isSelected())
            {
                return -1;
            }
            else if (p1.isSelected() && !p2.isSelected())
            {
                return 1;
            }
            else
            {
                if (!p1.category().equals(p2.category()))
                {
                    return p1.category().compareTo(p2.category());
                }
                else
                {
                    return p1.name().compareTo(p2.name());
                }
            }
        });
    }

    @Override
    protected ViewHolder viewHolder(View view)
    {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends BaseViewHolder
    {
        @BindView(R.id.product_row)
        public View row;

        @BindView(R.id.product_image)
        public ImageView image;

        @BindView(R.id.product_name)
        public TextView name;

        @BindView(R.id.product_check)
        public ImageView check;

        protected ViewHolder(View view)
        {
            super(view);
        }
    }
}