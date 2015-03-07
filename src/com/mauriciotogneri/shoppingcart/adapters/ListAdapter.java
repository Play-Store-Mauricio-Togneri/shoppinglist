package com.mauriciotogneri.shoppingcart.adapters;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.objects.Product;

public class ListAdapter extends ArrayAdapter<Product>
{
	private final Context context;
	private final LayoutInflater inflater;
	
	public ListAdapter(Context context, List<Product> list)
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
			convertView = this.inflater.inflate(R.layout.activity_list_row, null);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(product.getName());
		
		if (product.isSelected())
		{
			name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		else
		{
			name.setPaintFlags(name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		TextView quantity = (TextView)convertView.findViewById(R.id.quantity);
		quantity.setText(this.context.getString(R.string.list_quantity) + " " + product.getQuantity());
		
		if (product.isSelected())
		{
			quantity.setPaintFlags(quantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		else
		{
			quantity.setPaintFlags(quantity.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		ImageView thumbnail = (ImageView)convertView.findViewById(R.id.thumbnail);
		thumbnail.setImageResource(product.getThumbnail());
		
		CheckBox selected = (CheckBox)convertView.findViewById(R.id.selected);
		selected.setChecked(product.isSelected());
		
		return convertView;
	}
}