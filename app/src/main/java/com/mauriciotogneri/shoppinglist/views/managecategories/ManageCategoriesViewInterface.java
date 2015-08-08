package com.mauriciotogneri.shoppinglist.views.managecategories;

import java.util.List;
import android.content.Context;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.BaseViewInterface;

public interface ManageCategoriesViewInterface extends BaseViewInterface
{
	void initialize(Context context, List<Category> list, ManageCategoriesViewObserver observer);
	
	void editCategory(Context context, Category category, ManageCategoriesViewObserver observer);
	
	void showError(Context context);
	
	void refreshList(List<Category> list);
}