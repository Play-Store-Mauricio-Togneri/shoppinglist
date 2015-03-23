package com.mauriciotogneri.shoppingcart.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.DatabaseInitializer;
import com.mauriciotogneri.shoppingcart.views.CartView;

public class CartActivity extends BaseActivity<CartView> implements CartView.Observer
{
	private static final String ATTRIBUTE_FIRST_LAUNCH = "first_launch";
	
	@Override
	protected void initialize()
	{
		this.view.initialize(this, this);
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
			this.view.showToast(this, R.string.error_cart_empty);
		}
	}
	
	@Override
	public void onCartItemSelected(CartItem cartItem)
	{
		cartItem.invertSelection();
		cartItem.save();
		
		this.view.refreshList(true);
	}
	
	@Override
	public void onQuantityUpdated(CartItem cartItem, int value)
	{
		cartItem.setQuantity(value);
		cartItem.save();
		
		this.view.refreshList(false);
	}
	
	@Override
	public void onCartItemRemoved(CartItem cartItem)
	{
		cartItem.delete();
		
		this.view.removeCartItem(cartItem);
		this.view.refreshList(false);
	}
	
	@Override
	public void onAddProduct()
	{
		startActivity(AddProductActivity.class);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		this.view.refreshList(true);
		
		SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(this);
		
		if (preferencces.getBoolean(CartActivity.ATTRIBUTE_FIRST_LAUNCH, true))
		{
			DatabaseInitializer databaseInitializer = new DatabaseInitializer(this);
			databaseInitializer.execute();
			
			preferencces.edit().putBoolean(CartActivity.ATTRIBUTE_FIRST_LAUNCH, false).commit();
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