package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.ListCartItemAdapter.ViewHolder;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.widgets.CustomImageView;

public class ListCartItemAdapter extends BaseListAdapter<CartItem, ViewHolder>
{
	public ListCartItemAdapter(Context context)
	{
		super(context, R.layout.list_cart_item_row, new ArrayList<CartItem>());
	}
	
	@Override
	protected ViewHolder getViewHolder(View view)
	{
		return new ViewHolder(view);
	}
	
	@Override
	protected void fillView(ViewHolder viewHolder, CartItem cartItem, int position)
	{
		if (position == 0)
		{
			setCategoryHeader(viewHolder, cartItem);
		}
		else
		{
			CartItem previousCartItem = getItem(position - 1);
			
			boolean sameCategory = previousCartItem.getCategory().getName().equals(cartItem.getCategory().getName());
			boolean bothNotSelected = (!cartItem.isSelected()) && (!previousCartItem.isSelected());
			boolean firstItemSelected = cartItem.isSelected() && (!previousCartItem.isSelected());
			
			if ((!sameCategory && bothNotSelected) || (firstItemSelected))
			{
				setCategoryHeader(viewHolder, cartItem);
			}
			else
			{
				viewHolder.categoryHeader.setVisibility(View.GONE);
			}
		}
		
		viewHolder.name.setText(cartItem.getName());
		
		if (cartItem.isSelected())
		{
			viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		else
		{
			viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		viewHolder.quantity.setText(getContext().getString(R.string.label_quantity) + "   " + cartItem.getQuantity());
		
		if (cartItem.isSelected())
		{
			viewHolder.quantity.setPaintFlags(viewHolder.quantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		else
		{
			viewHolder.quantity.setPaintFlags(viewHolder.quantity.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		viewHolder.thumbnail.setImage(cartItem.getImage(), cartItem.isSelected());
		
		viewHolder.selected.setChecked(cartItem.isSelected());
	}
	
	private void setCategoryHeader(ViewHolder viewHolder, CartItem cartItem)
	{
		viewHolder.categoryHeader.setVisibility(View.VISIBLE);
		
		if (cartItem.isSelected())
		{
			viewHolder.categoryHeader.setBackgroundColor(Color.parseColor("#" + Category.COLOR_1));
			viewHolder.categoryName.setText(R.string.label_already_in_cart);
		}
		else
		{
			viewHolder.categoryHeader.setBackgroundColor(cartItem.getCategory().getIntColor());
			viewHolder.categoryName.setText(cartItem.getCategory().getName());
		}
	}
	
	protected static class ViewHolder
	{
		public final LinearLayout categoryHeader;
		public final TextView categoryName;
		public final CustomImageView thumbnail;
		public final TextView name;
		public final TextView quantity;
		public final CheckBox selected;
		
		public ViewHolder(View view)
		{
			this.categoryHeader = (LinearLayout)view.findViewById(R.id.category_header);
			this.categoryName = (TextView)view.findViewById(R.id.category_name);
			this.thumbnail = (CustomImageView)view.findViewById(R.id.thumbnail);
			this.name = (TextView)view.findViewById(R.id.name);
			this.quantity = (TextView)view.findViewById(R.id.quantity);
			this.selected = (CheckBox)view.findViewById(R.id.selected);
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