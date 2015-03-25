package com.mauriciotogneri.shoppingcart.fragments;

import java.util.List;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.dao.CartItemDao;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.DatabaseInitializer;
import com.mauriciotogneri.shoppingcart.views.CartView;

public class CartFragment extends BaseFragment<CartView> implements CartView.Observer
{
	private static final String ATTRIBUTE_FIRST_LAUNCH = "first_launch";
	
	@Override
	protected void initialize()
	{
		this.view.initialize(getContext(), this);
	}
	
	@Override
	public void onShare()
	{
		String shareContent = this.view.getShareContent();
		
		if (!shareContent.isEmpty())
		{
			share(R.string.label_share_cart, shareContent);
		}
		else
		{
			this.view.showToast(getContext(), R.string.error_cart_empty);
		}
	}
	
	private void updateList()
	{
		CartItemDao cartItemDao = new CartItemDao();
		List<CartItem> list = cartItemDao.getCartItems();
		
		this.view.refreshList(list);
	}
	
	private void refreshList()
	{
		this.view.refreshList();
	}
	
	@Override
	public void onCartItemSelected(CartItem cartItem)
	{
		cartItem.invertSelection();
		cartItem.save();
		
		updateList();
	}
	
	@Override
	public void onQuantityUpdated(CartItem cartItem, int value)
	{
		cartItem.setQuantity(value);
		cartItem.save();
		
		refreshList();
	}
	
	@Override
	public void onCartItemRemoved(CartItem cartItem)
	{
		cartItem.delete();
		
		this.view.removeCartItem(cartItem);
		refreshList();
	}
	
	@Override
	public void onAddProduct()
	{
		AddProductFragment fragment = new AddProductFragment();
		startFragment(fragment);
	}
	
	@Override
	public void onActivate()
	{
		updateList();
		
		SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(getContext());
		
		if (preferencces.getBoolean(CartFragment.ATTRIBUTE_FIRST_LAUNCH, true))
		{
			DatabaseInitializer databaseInitializer = new DatabaseInitializer(getContext());
			databaseInitializer.execute();
			
			preferencces.edit().putBoolean(CartFragment.ATTRIBUTE_FIRST_LAUNCH, false).commit();
		}
	}
	
	@Override
	protected void onFinish()
	{
		this.view.removeSelectedItems();
	}
	
	@Override
	protected Class<CartView> getViewClass()
	{
		return CartView.class;
	}
}