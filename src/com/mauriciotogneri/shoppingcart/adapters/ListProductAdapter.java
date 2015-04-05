package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomImageView;

public class ListProductAdapter extends ArrayAdapter<Product>
{
	public ListProductAdapter(Context context)
	{
		super(context, R.layout.list_product_row, R.id.name, new ArrayList<Product>());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = super.getView(position, convertView, parent);
		
		ViewHolder viewHolder = (ViewHolder)rowView.getTag();
		
		if (viewHolder == null)
		{
			viewHolder = new ViewHolder(rowView);
			rowView.setTag(viewHolder);
		}
		
		Product product = getItem(position);
		
		viewHolder.name.setText(product.getName());
		viewHolder.thumbnail.setImage(product.getImage());
		
		return rowView;
	}
	
	public void refresh(List<Product> list)
	{
		clear();
		addAll(list);
		notifyDataSetChanged();
	}
	
	private static class ViewHolder
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