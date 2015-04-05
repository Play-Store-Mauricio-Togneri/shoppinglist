package com.mauriciotogneri.shoppingcart.adapters;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.ListProductAdapter.ViewHolder;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomImageView;

public class ListProductAdapter extends BaseListAdapter<Product, ViewHolder>
{
	public ListProductAdapter(Context context)
	{
		super(context, R.layout.list_product_row);
	}
	
	@Override
	protected ViewHolder getViewHolder(View view)
	{
		return new ViewHolder(view);
	}
	
	@Override
	protected void fillView(ViewHolder viewHolder, Product product, int position)
	{
		viewHolder.name.setText(product.getName());
		viewHolder.thumbnail.setImage(product.getImage());
	}
	
	public void refresh(List<Product> list)
	{
		clear();
		addAll(list);
		notifyDataSetChanged();
	}
	
	protected static class ViewHolder
	{
		public TextView name;
		public CustomImageView thumbnail;
		
		public ViewHolder(View view)
		{
			this.name = (TextView)view.findViewById(R.id.name);
			this.thumbnail = (CustomImageView)view.findViewById(R.id.thumbnail);
		}
	}
}