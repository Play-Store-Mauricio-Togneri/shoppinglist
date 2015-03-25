package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomImageView;

public class ListProductAdapter extends ArrayAdapter<Product>
{
	private final LayoutInflater inflater;
	
	public ListProductAdapter(Context context)
	{
		super(context, android.R.layout.simple_list_item_1, new ArrayList<Product>());
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	@SuppressLint("InflateParams")
	public View getView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		Product product = getItem(position);
		
		if (convertView == null)
		{
			convertView = this.inflater.inflate(R.layout.list_product_row, parent, false);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(product.getName());
		
		CustomImageView productImage = (CustomImageView)convertView.findViewById(R.id.thumbnail);
		productImage.setImage(product.getImage());
		
		return convertView;
	}
	
	public void refresh(List<Product> list)
	{
		clear();
		addAll(list);
		notifyDataSetChanged();
	}
}