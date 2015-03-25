package com.mauriciotogneri.shoppingcart.views.updateproduct;

import java.util.List;
import android.content.Context;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.BaseViewInterface;

public interface UpdateProductViewInterface extends BaseViewInterface
{
	void initialize(Context context, Product product, Category initialCategory, List<Category> list, UpdateProductViewObserver observer);
	
	void setProductImage(byte[] image);
	
	void setNameInputError(Context context, int textId);
	
	void removeInputNameError();
	
	Category getProductCategory();
	
	String getProductName();
	
	void refreshCategories(List<Category> list);
	
	void setCategory(Category category);
}