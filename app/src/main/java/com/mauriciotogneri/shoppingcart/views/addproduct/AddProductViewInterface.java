package com.mauriciotogneri.shoppingcart.views.addproduct;

import java.util.List;
import android.content.Context;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.BaseViewInterface;

public interface AddProductViewInterface extends BaseViewInterface
{
	void initialize(Context context, AddProductViewObserver observer);
	
	void showError(Context context);
	
	void refreshList(List<Product> list);
	
	void refreshCategories(List<Category> list);
	
	Category getSelectedCategory();
	
	void setCategory(Category category);
}