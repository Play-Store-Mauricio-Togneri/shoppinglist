package com.mauriciotogneri.shoppingcart.fragments;

import java.util.List;
import android.os.Bundle;
import com.mauriciotogneri.shoppingcart.dao.CartItemDao;
import com.mauriciotogneri.shoppingcart.dao.CategoryDao;
import com.mauriciotogneri.shoppingcart.dao.ProductDao;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.addproduct.AddProductView;
import com.mauriciotogneri.shoppingcart.views.addproduct.AddProductViewInterface;
import com.mauriciotogneri.shoppingcart.views.addproduct.AddProductViewObserver;

public class AddProductFragment extends BaseFragment<AddProductViewInterface> implements AddProductViewObserver
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
		
		refreshList(this.view.getSelectedCategory());
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
			refreshList(this.view.getSelectedCategory());
		}
		else
		{
			this.view.showError(getContext());
		}
	}
	
	private void refreshCategories()
	{
		CategoryDao categoryDao = new CategoryDao();
		List<Category> list = categoryDao.getCategories();
		
		this.view.refreshCategories(list);
	}
	
	@Override
	public void onActivate()
	{
		refreshCategories();
		refreshList(this.view.getSelectedCategory());
	}
	
	@Override
	public void onActivate(Object result)
	{
		refreshCategories();
		this.view.setCategory((Category)result);
		refreshList(this.view.getSelectedCategory());
	}
	
	@Override
	public void onCategorySelected(Category category)
	{
		refreshList(category);
	}
	
	private void refreshList(Category category)
	{
		if (category != null)
		{
			ProductDao productDao = new ProductDao();
			List<Product> list = productDao.getProducts(category);
			
			this.view.refreshList(list);
		}
	}
	
	@Override
	protected AddProductViewInterface getViewInstance()
	{
		return new AddProductView();
	}
}