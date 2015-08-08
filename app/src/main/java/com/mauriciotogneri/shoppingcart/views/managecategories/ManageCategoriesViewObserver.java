package com.mauriciotogneri.shoppingcart.views.managecategories;

import com.mauriciotogneri.shoppingcart.model.Category;

public interface ManageCategoriesViewObserver
{
	void onCategorySelected(Category category);
	
	void onEditCategory(Category category, String name, String color);
	
	void onRemoveCategory(Category category);
}