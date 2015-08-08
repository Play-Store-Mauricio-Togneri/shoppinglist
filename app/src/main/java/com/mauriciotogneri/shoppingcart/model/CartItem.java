package com.mauriciotogneri.shoppingcart.model;

import com.orm.SugarRecord;

public class CartItem extends SugarRecord<CartItem>
{
	private Product product;
	private int quantity;
	private boolean selected;
	
	public CartItem()
	{
	}
	
	public CartItem(Product product, int quantity, boolean selected)
	{
		this.product = product;
		this.quantity = quantity;
		this.selected = selected;
	}
	
	public String getName()
	{
		return this.product.getName();
	}
	
	public String getCategoryName()
	{
		return this.product.getCategory().getName();
	}
	
	public byte[] getImage()
	{
		return this.product.getImage();
	}
	
	public int getQuantity()
	{
		return this.quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public boolean isSelected()
	{
		return this.selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	public Category getCategory()
	{
		return this.product.getCategory();
	}
	
	public void invertSelection()
	{
		this.selected = !this.selected;
	}
}