package com.mauriciotogneri.shoppingcart.adapters;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.ProductOptionAdapter.Option;

public class ProductOptionAdapter extends ArrayAdapter<Option>
{
	private final LayoutInflater inflater;
	
	public ProductOptionAdapter(Context context, List<Option> options)
	{
		super(context, android.R.layout.simple_list_item_1, options);
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	@SuppressLint("InflateParams")
	public View getView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		Option option = getItem(position);
		
		if (convertView == null)
		{
			convertView = this.inflater.inflate(R.layout.dialog_product_options_row, null);
		}
		
		TextView icon = (TextView)convertView.findViewById(R.id.icon);
		icon.setText(option.icon);
		
		TextView title = (TextView)convertView.findViewById(R.id.title);
		title.setText(option.title);
		
		return convertView;
	}
	
	public static class Option
	{
		public final String icon;
		public final String title;
		
		public Option(String icon, String title)
		{
			this.icon = icon;
			this.title = title;
		}
	}
}