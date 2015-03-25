package com.mauriciotogneri.shoppingcart.views.cart;

import java.util.List;
import android.content.Context;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.views.BaseViewInterface;

public interface CartViewInterface extends BaseViewInterface
{
	void initialize(Context context, CartViewObserver observer);
	
	void removeCartItem(CartItem cartItem);
	
	void removeSelectedItems();
	
	String getShareContent();
	
	void refreshList(List<CartItem> list);
	
	void refreshList();
}