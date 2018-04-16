package com.mauriciotogneri.shoppinglist;

import android.graphics.Paint;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.shoppinglist.CartElementAdapter.ViewHolder;

import java.util.Collections;
import java.util.List;

public class CartElementAdapter extends WearableRecyclerView.Adapter<ViewHolder>
{
    private final MainActivity mainActivity;
    private final List<CartElement> cartElements;

    public CartElementAdapter(MainActivity mainActivity, List<CartElement> cartElements)
    {
        this.mainActivity = mainActivity;
        this.cartElements = cartElements;
    }

    public void sort()
    {
        Collections.sort(cartElements, (p1, p2) ->
        {
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
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        CartElement element = cartElements.get(position);

        viewHolder.row.setOnClickListener(view -> mainActivity.onProductSelected(element));
        viewHolder.name.setText(element.name());

        if (element.isSelected())
        {
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() | (Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else
        {
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount()
    {
        return cartElements.size();
    }

    public static class ViewHolder extends WearableRecyclerView.ViewHolder
    {
        private final View row;
        private final TextView name;

        public ViewHolder(View view)
        {
            super(view);

            row = view;
            name = view.findViewById(R.id.product_name);
        }
    }
}