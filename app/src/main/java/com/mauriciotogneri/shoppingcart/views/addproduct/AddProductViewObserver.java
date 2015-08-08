package com.mauriciotogneri.shoppingcart.views.addproduct;

import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;

public interface AddProductViewObserver
{
	void onAddProduct(Product product, int value);
	
	void onCreateProduct();
	
	void onEditProduct(Product product);
	
	void onRemoveProduct(Product product);
	
	void onCategorySelected(Category category);
}
