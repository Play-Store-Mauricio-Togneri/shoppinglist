package com.mauriciotogneri.shoppingcart.model;

import com.activeandroid.Model;

public class CartItem extends Model
{
	private long productId;
	private int quantity;
	private boolean selected;
	
	public CartItem()
	{
	}
	
	public CartItem(long productId, int quantity, boolean selected)
	{
		this.productId = productId;
		this.quantity = quantity;
		this.selected = selected;
	}
	
	public long getProductId()
	{
		return this.productId;
	}
	
	public int getQuantity()
	{
		return this.quantity;
	}
	
	public boolean isSelected()
	{
		return this.selected;
	}
}