package com.mauriciotogneri.shoppinglist.views.updateproduct;

import com.mauriciotogneri.shoppinglist.model.Category;

public interface UpdateProductViewObserver
{
	void onManageCategories();
	
	void onUpdateImageGalery();
	
	void onUpdateImageCamera();
	
	void onUpdateProduct();
	
	void onCategorySelected(Category category);
}