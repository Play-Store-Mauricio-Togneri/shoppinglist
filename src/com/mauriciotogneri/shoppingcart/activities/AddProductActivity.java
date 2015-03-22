package com.mauriciotogneri.shoppingcart.activities;

import android.content.Intent;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.AddProductView;

public class AddProductActivity extends BaseActivity<AddProductView> implements AddProductView.Events
{
	@Override
	protected void initialize()
	{
		this.view.initialize(this, this);
	}
	
	@Override
	public void onAddProduct(Product product, int value)
	{
		CartItem cartItem = new CartItem(product, value, false);
		cartItem.save();
		
		this.view.refreshList();
	}
	
	@Override
	public void onCreateProduct()
	{
		Intent intent = new Intent(this, UpdateProductActivity.class);
		intent.putExtra(UpdateProductActivity.PARAMETER_CATEGORY, this.view.getSelectedCategory());
		startActivity(intent);
	}
	
	@Override
	public void onEditProduct(Product product)
	{
		Intent intent = new Intent(this, UpdateProductActivity.class);
		intent.putExtra(UpdateProductActivity.PARAMETER_PRODUCT_ID, product.getId());
		startActivity(intent);
	}
	
	@Override
	public void onRemoveProduct(Product product)
	{
		if (!CartItem.exists(product))
		{
			product.delete();
			this.view.refreshList();
		}
		else
		{
			this.view.showError(this);
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		this.view.refreshCategories();
		this.view.refreshList();
	}
	
	@Override
	protected Class<AddProductView> getViewClass()
	{
		return AddProductView.class;
	}
}