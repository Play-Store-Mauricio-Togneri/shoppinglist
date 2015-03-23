package com.mauriciotogneri.shoppingcart.fragments;

import android.text.TextUtils;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.ManageCategoriesView;

public class ManageCategoriesFragment extends BaseFragment<ManageCategoriesView> implements ManageCategoriesView.Observer
{
	@Override
	protected void initialize()
	{
		this.view.initialize(getContext(), this);
	}
	
	@Override
	public void onEditCategory(Category category, String name, String color)
	{
		if (!TextUtils.isEmpty(name))
		{
			if (category == null)
			{
				if (!Category.exists(name))
				{
					Category newCategory = new Category(name, color);
					newCategory.save();
					
					this.view.refreshList();
				}
				else
				{
					this.view.editCategory(getContext(), category, this);
					this.view.showToast(getContext(), R.string.error_category_already_exists);
				}
			}
			else
			{
				if ((!category.getName().equals(name)) && Category.exists(name))
				{
					this.view.editCategory(getContext(), category, this);
					this.view.showToast(getContext(), R.string.error_category_already_exists);
				}
				else
				{
					category.update(name, color);
					this.view.refreshList();
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
		if (!Product.exists(category))
		{
			category.delete();
			this.view.refreshList();
		}
		else
		{
			this.view.showError(getContext());
		}
	}
	
	@Override
	public void onCategorySelected(Category category)
	{
		setResult(category);
		close();
	}
	
	@Override
	protected Class<ManageCategoriesView> getViewClass()
	{
		return ManageCategoriesView.class;
	}
}