package com.mauriciotogneri.shoppingcart.views.updateproduct;

import com.mauriciotogneri.shoppingcart.model.Category;

public interface UpdateProductViewObserver
{
	void onManageCategories();
	
	void onUpdateImageGalery();
	
	void onUpdateImageCamera();
	
	void onUpdateProduct();
	
	void onCategorySelected(Category category);
}