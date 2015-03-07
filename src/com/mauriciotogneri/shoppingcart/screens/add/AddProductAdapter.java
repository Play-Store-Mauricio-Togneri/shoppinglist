package com.mauriciotogneri.shoppingcart.screens.add;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.objects.Product;

public class AddProductAdapter extends ArrayAdapter<Product>
{
	private final LayoutInflater inflater;
	
	public AddProductAdapter(Context context, List<Product> list)
	{
		super(context, android.R.layout.simple_list_item_1, list);
		
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
			convertView = this.inflater.inflate(R.layout.activity_add_product_row, null);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(product.getName());
		name.setCompoundDrawablePadding(15);
		name.setCompoundDrawablesWithIntrinsicBounds(product.getThumbnail(), 0, 0, 0);
		
		return convertView;
	}
}