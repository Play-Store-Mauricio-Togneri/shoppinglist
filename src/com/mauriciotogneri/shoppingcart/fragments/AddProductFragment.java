package com.mauriciotogneri.shoppingcart.fragments;

import android.os.Bundle;
import com.mauriciotogneri.shoppingcart.dao.CartItemDao;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.AddProductView;

public class AddProductFragment extends BaseFragment<AddProductView> implements AddProductView.Observer
{
	@Override
	protected void initialize()
	{
		this.view.initialize(getContext(), this);
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
		UpdateProductFragment fragment = new UpdateProductFragment();
		Bundle parameters = new Bundle();
		parameters.putSerializable(UpdateProductFragment.PARAMETER_CATEGORY, this.view.getSelectedCategory());
		fragment.setArguments(parameters);
		startFragment(fragment);
	}
	
	@Override
	public void onEditProduct(Product product)
	{
		UpdateProductFragment fragment = new UpdateProductFragment();
		Bundle parameters = new Bundle();
		parameters.putLong(UpdateProductFragment.PARAMETER_PRODUCT_ID, product.getId());
		fragment.setArguments(parameters);
		startFragment(fragment);
	}
	
	@Override
	public void onRemoveProduct(Product product)
	{
		CartItemDao cartItemDao = new CartItemDao();
		
		if (!cartItemDao.exists(product))
		{
			product.delete();
			this.view.refreshList();
		}
		else
		{
			this.view.showError(getContext());
		}
	}
	
	@Override
	public void onActivate()
	{
		this.view.refreshCategories();
		this.view.refreshList();
	}
	
	@Override
	public void onActivate(Object result)
	{
		this.view.setCategory((Category)result);
		this.view.refreshList();
	}
	
	@Override
	protected Class<AddProductView> getViewClass()
	{
		return AddProductView.class;
	}
}