package com.mauriciotogneri.shoppinglist.views.addproduct;

import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;

public interface AddProductViewObserver
{
	void onAddProduct(Product product, int value);
	
	void onCreateProduct();
	
	void onEditProduct(Product product);
	
	void onRemoveProduct(Product product);
	
	void onCategorySelected(Category category);
}
