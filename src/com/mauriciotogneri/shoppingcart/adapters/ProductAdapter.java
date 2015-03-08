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
import com.mauriciotogneri.shoppingcart.model.Product;

public class ProductAdapter extends ArrayAdapter<Product>
{
	private final Context context;
	private final LayoutInflater inflater;
	
	public ProductAdapter(Context context, List<Product> list)
	{
		super(context, android.R.layout.simple_list_item_1, list);
		
		this.context = context;
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
		
		TextView name = (TextView)convertView.findViewById(R.id.title);
		name.setText(product.getName());
		name.setCompoundDrawablePadding(20);
		byte[] picture = product.getPicture();
		name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.product_bananas, 0, 0, 0);
		
		if (position % 2 == 0)
		{
			convertView.setBackgroundColor(this.context.getResources().getColor(R.color.row_background_color_even));
		}
		else
		{
			convertView.setBackgroundColor(this.context.getResources().getColor(R.color.row_background_color_odd));
		}
		
		return convertView;
	}
}