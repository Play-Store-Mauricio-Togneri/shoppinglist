package com.mauriciotogneri.shoppinglist.adapters;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ListProductAdapter.ViewHolder;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.widgets.CustomImageView;

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
		public final TextView name;
		public final CustomImageView thumbnail;
		
		public ViewHolder(View view)
		{
			this.name = (TextView)view.findViewById(R.id.name);
			this.thumbnail = (CustomImageView)view.findViewById(R.id.thumbnail);
		}
	}
}