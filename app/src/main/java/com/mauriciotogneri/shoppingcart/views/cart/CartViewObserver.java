package com.mauriciotogneri.shoppingcart.views.cart;

import com.mauriciotogneri.shoppingcart.model.CartItem;

public interface CartViewObserver
{
	void onCartItemSelected(CartItem cartItem);
	
	void onShare();
	
	void onAddProduct();
	
	void onQuantityUpdated(CartItem cartItem, int value);
	
	void onCartItemRemoved(CartItem cartItem);
}