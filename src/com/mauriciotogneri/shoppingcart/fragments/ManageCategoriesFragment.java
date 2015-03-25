package com.mauriciotogneri.shoppingcart.fragments;

import java.util.List;
import android.text.TextUtils;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.dao.CategoryDao;
import com.mauriciotogneri.shoppingcart.dao.ProductDao;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.views.managecategories.ManageCategoriesView;
import com.mauriciotogneri.shoppingcart.views.managecategories.ManageCategoriesViewInterface;
import com.mauriciotogneri.shoppingcart.views.managecategories.ManageCategoriesViewObserver;

public class ManageCategoriesFragment extends BaseFragment<ManageCategoriesViewInterface> implements ManageCategoriesViewObserver
{
	@Override
	protected void initialize()
	{
		List<Category> list = getCategories();
		
		this.view.initialize(getContext(), list, this);
	}
	
	@Override
	public void onEditCategory(Category category, String name, String color)
	{
		if (!TextUtils.isEmpty(name))
		{
			if (category == null)
			{
				CategoryDao categoryDao = new CategoryDao();
				
				if (!categoryDao.exists(name))
				{
					Category newCategory = new Category(name, color);
					newCategory.save();
					
					refreshList();
				}
				else
				{
					this.view.editCategory(getContext(), category, this);
					this.view.showToast(getContext(), R.string.error_category_already_exists);
				}
			}
			else
			{
				CategoryDao categoryDao = new CategoryDao();
				
				if ((!category.getName().equals(name)) && categoryDao.exists(name))
				{
					this.view.editCategory(getContext(), category, this);
					this.view.showToast(getContext(), R.string.error_category_already_exists);
				}
				else
				{
					category.update(name, color);
					refreshList();
				}
			}
		}
		else
		{
			this.view.editCategory(getContext(), category, this);
			this.view.showToast(getContext(), R.string.error_invalid_name);
		}
	}
	
	@Override
	public void onRemoveCategory(Category category)
	{
		ProductDao productDao = new ProductDao();
		
		if (!productDao.exists(category))
		{
			category.delete();
			refreshList();
		}
		else
		{
			this.view.showError(getContext());
		}
	}
	
	private void refreshList()
	{
		List<Category> list = getCategories();
		
		this.view.refreshList(list);
	}
	
	private List<Category> getCategories()
	{
		CategoryDao categoryDao = new CategoryDao();
		
		return categoryDao.getCategories();
	}
	
	@Override
	public void onCategorySelected(Category category)
	{
		setResult(category);
		close();
	}
	
	@Override
	protected ManageCategoriesView getViewInstance()
	{
		return new ManageCategoriesView();
	}
}