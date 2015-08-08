package com.mauriciotogneri.shoppinglist.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.mauriciotogneri.shoppinglist.model.CartItem;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.orm.query.Select;

public class CartItemDao
{
	public List<CartItem> getCartItems()
	{
		List<CartItem> result = new ArrayList<CartItem>();
		List<CartItem> cartItems = Select.from(CartItem.class).list();
		
		List<CartItem> notSelected = new ArrayList<CartItem>();
		List<CartItem> selected = new ArrayList<CartItem>();
		
		for (CartItem cartItem : cartItems)
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
		
		result.addAll(notSelected);
		
		if (!selected.isEmpty())
		{
			result.addAll(selected);
		}
		
		return result;
	}
	
	public boolean exists(Product product)
	{
		CartItem cartItem = Select.from(CartItem.class).where("product = ?", new String[]
			{
				String.valueOf(product.getId())
			}).first();
		
		return (cartItem != null);
	}
}