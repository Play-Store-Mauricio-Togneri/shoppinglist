package com.mauriciotogneri.shoppingcart.model;

import java.util.List;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

public class CartItem extends Model
{
	@Column(name = "product")
	private long productId;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "selected")
	private boolean selected;
	
	private Product product;
	
	public CartItem()
	{
	}
	
	public CartItem(Product product, int quantity, boolean selected)
	{
		this.productId = product.getId();
		this.quantity = quantity;
		this.selected = selected;
	}
	
	private void setProduct()
	{
		if (this.product == null)
		{
			this.product = new Select().from(Product.class).where("id = ?", this.productId).executeSingle();
		}
	}
	
	public String getName()
	{
		setProduct();
		
		return this.product.getName();
	}
	
	public String getCategoryName()
	{
		setProduct();
		
		return this.product.getCategory().getName();
	}
	
	public byte[] getImage()
	{
		setProduct();
		
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
		setProduct();
		
		return this.product.getCategory();
	}
	
	public void invertSelection()
	{
		this.selected = !this.selected;
	}
	
	public static boolean exists(Product product)
	{
		List<CartItem> cartItems = new Select().from(CartItem.class).where("product = ?", product.getId()).execute();
		
		return (cartItems != null) && (!cartItems.isEmpty());
	}
}