package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class CartItemAdapter extends ArrayAdapter<CartItem>
{
	private final Context context;
	private final LayoutInflater inflater;
	
	public CartItemAdapter(Context context)
	{
		super(context, android.R.layout.simple_list_item_1, new ArrayList<CartItem>());
		
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	@SuppressLint("InflateParams")
	public View getView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		CartItem cartItem = getItem(position);
		
		if (convertView == null)
		{
			convertView = this.inflater.inflate(R.layout.activity_main_row, null);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(cartItem.getName());
		
		if (cartItem.isSelected())
		{
			name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		else
		{
			name.setPaintFlags(name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		TextView quantity = (TextView)convertView.findViewById(R.id.quantity);
		quantity.setText(this.context.getString(R.string.list_quantity) + "   " + cartItem.getQuantity());
		
		if (cartItem.isSelected())
		{
			quantity.setPaintFlags(quantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		else
		{
			quantity.setPaintFlags(quantity.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		ProductImage productImage = (ProductImage)convertView.findViewById(R.id.thumbnail);
		productImage.setImage(cartItem.getImage(), cartItem.isSelected());
		
		CheckBox selected = (CheckBox)convertView.findViewById(R.id.selected);
		selected.setChecked(cartItem.isSelected());
		
		return convertView;
	}
	
	public void update(boolean sort)
	{
		if (sort)
		{
			List<CartItem> notSelected = new ArrayList<CartItem>();
			List<CartItem> selected = new ArrayList<CartItem>();
			
			int limit = getCount();
			
			for (int i = 0; i < limit; i++)
			{
				CartItem cartItem = getItem(i);
				
				if (cartItem.isSelected())
				{
					selected.add(cartItem);
				}
				else
				{
					notSelected.add(cartItem);
				}
			}
			
			Collections.sort(notSelected, new Comparator<CartItem>()
			{
				@Override
				public int compare(CartItem lhs, CartItem rhs)
				{
					return lhs.getName().compareTo(rhs.getName());
				}
			});
			
			Collections.sort(selected, new Comparator<CartItem>()
			{
				@Override
				public int compare(CartItem lhs, CartItem rhs)
				{
					return lhs.getName().compareTo(rhs.getName());
				}
			});
			
			clear();
			addAll(notSelected);
			addAll(selected);
		}
		
		notifyDataSetChanged();
	}
	
	public void removeSelectedItems()
	{
		int limit = getCount();
		
		for (int i = 0; i < limit; i++)
		{
			CartItem cartItem = getItem(i);
			
			if (cartItem.isSelected())
			{
				cartItem.delete();
			}
		}
	}
}