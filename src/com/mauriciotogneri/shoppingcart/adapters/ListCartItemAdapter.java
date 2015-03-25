package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class ListCartItemAdapter extends ArrayAdapter<CartItem>
{
	private final Context context;
	private final LayoutInflater inflater;
	
	public ListCartItemAdapter(Context context)
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
			convertView = this.inflater.inflate(R.layout.list_cart_item_row, parent, false);
		}
		
		LinearLayout categoryHeader = (LinearLayout)convertView.findViewById(R.id.category_header);
		
		if (position == 0)
		{
			setCategoryHeader(categoryHeader, cartItem, convertView);
		}
		else
		{
			CartItem previousCartItem = getItem(position - 1);
			
			boolean sameCategory = previousCartItem.getCategory().getName().equals(cartItem.getCategory().getName());
			boolean bothNotSelected = (!cartItem.isSelected()) && (!previousCartItem.isSelected());
			boolean firstItemSelected = cartItem.isSelected() && (!previousCartItem.isSelected());
			
			if ((!sameCategory && bothNotSelected) || (firstItemSelected))
			{
				setCategoryHeader(categoryHeader, cartItem, convertView);
			}
			else
			{
				categoryHeader.setVisibility(View.GONE);
			}
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
		quantity.setText(this.context.getString(R.string.label_quantity) + "   " + cartItem.getQuantity());
		
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
	
	private void setCategoryHeader(LinearLayout categoryHeader, CartItem cartItem, View convertView)
	{
		categoryHeader.setVisibility(View.VISIBLE);
		
		TextView title = (TextView)convertView.findViewById(R.id.category_name);
		
		if (cartItem.isSelected())
		{
			categoryHeader.setBackgroundColor(Color.parseColor("#" + Category.COLOR_1));
			title.setText(R.string.label_already_in_cart);
		}
		else
		{
			categoryHeader.setBackgroundColor(cartItem.getCategory().getIntColor());
			title.setText(cartItem.getCategory().getName());
		}
	}
	
	public void refresh(List<CartItem> list)
	{
		clear();
		addAll(list);
		notifyDataSetChanged();
	}
	
	public void refresh()
	{
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
	
	public String getShareContent()
	{
		StringBuilder result = new StringBuilder();
		
		int limit = getCount();
		String lastCategory = "";
		
		for (int i = 0; i < limit; i++)
		{
			CartItem cartItem = getItem(i);
			
			if (!cartItem.isSelected())
			{
				if (!cartItem.getCategory().getName().equals(lastCategory))
				{
					lastCategory = cartItem.getCategory().getName();
					
					if (result.length() != 0)
					{
						result.append("\n");
					}
					
					result.append(lastCategory).append(":\n");
				}
				
				result.append("   - ").append(cartItem.getName()).append(" (").append(cartItem.getQuantity()).append(")\n");
			}
		}
		
		return result.toString();
	}
}