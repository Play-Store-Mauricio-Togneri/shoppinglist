package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.activeandroid.Model;
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
		
		if (cartItem instanceof CartItemSeparator)
		{
			CartItemSeparator separator = (CartItemSeparator)cartItem;
			
			convertView = this.inflater.inflate(R.layout.list_cart_item_separator, parent, false);
			
			LinearLayout separatorLayout = (LinearLayout)convertView.findViewById(R.id.separator);
			separatorLayout.setBackgroundColor(separator.getColor());
			
			TextView title = (TextView)convertView.findViewById(R.id.title);
			title.setText(separator.getTitle());
		}
		else
		{
			convertView = this.inflater.inflate(R.layout.list_cart_item_row, parent, false);
			
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
		}
		
		return convertView;
	}
	
	public void refresh(boolean sort)
	{
		if (sort)
		{
			List<CartItem> cartItems = Model.all(CartItem.class);
			
			List<CartItem> notSelected = new ArrayList<CartItem>();
			List<CartItem> selected = new ArrayList<CartItem>();
			
			for (CartItem cartItem : cartItems)
			{
				if (!(cartItem instanceof CartItemSeparator))
				{
					if (cartItem.isSelected())
					{
						selected.add(cartItem);
					}
					else
					{
						notSelected.add(cartItem);
					}
				}
			}
			
			Collections.sort(notSelected, new Comparator<CartItem>()
			{
				@Override
				public int compare(CartItem lhs, CartItem rhs)
				{
					return lhs.getCategoryName().compareTo(rhs.getCategoryName());
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
			
			notSelected = getGroupedCartItems(notSelected);
			
			clear();
			addAll(notSelected);
			
			if (!selected.isEmpty())
			{
				Category separatorCategory = new Category(getContext().getString(R.string.label_already_in_cart), Category.COLOR_1);
				
				selected.add(0, new CartItemSeparator(separatorCategory, true));
				addAll(selected);
			}
		}
		
		notifyDataSetChanged();
	}
	
	private List<CartItem> getGroupedCartItems(List<CartItem> cartItems)
	{
		List<CartItem> result = new ArrayList<CartItem>();
		
		Category lastCategory = null;
		
		for (CartItem cartItem : cartItems)
		{
			Category currentCategory = cartItem.getCategory();
			
			if ((lastCategory == null) || (!currentCategory.getName().equals(lastCategory.getName())))
			{
				lastCategory = currentCategory;
				result.add(new CartItemSeparator(lastCategory, false));
			}
			
			result.add(cartItem);
		}
		
		return result;
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
		
		for (int i = 0; i < limit; i++)
		{
			CartItem cartItem = getItem(i);
			
			if (cartItem instanceof CartItemSeparator)
			{
				CartItemSeparator separator = (CartItemSeparator)cartItem;
				
				if (!separator.isInCart())
				{
					if (result.length() != 0)
					{
						result.append("\n");
					}
					
					result.append(separator.getTitle()).append(":\n");
				}
			}
			else if (!cartItem.isSelected())
			{
				result.append("   * ").append(cartItem.getName()).append(" (").append(cartItem.getQuantity()).append(")\n");
			}
		}
		
		return result.toString();
	}
	
	public class CartItemSeparator extends CartItem
	{
		private final boolean inCart;
		private final String title;
		private final String color;
		
		public CartItemSeparator(Category category, boolean inCart)
		{
			this.inCart = inCart;
			this.title = category.getName();
			this.color = category.getColor();
		}
		
		public boolean isInCart()
		{
			return this.inCart;
		}
		
		public String getTitle()
		{
			return this.title;
		}
		
		public int getColor()
		{
			return Color.parseColor("#" + this.color);
		}
	}
}