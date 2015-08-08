package com.mauriciotogneri.shoppinglist.views.cart;

import com.mauriciotogneri.shoppinglist.model.CartItem;

public interface CartViewObserver
{
	void onCartItemSelected(CartItem cartItem);
	
	void onShare();
	
	void onAddProduct();
	
	void onQuantityUpdated(CartItem cartItem, int value);
	
	void onCartItemRemoved(CartItem cartItem);
}