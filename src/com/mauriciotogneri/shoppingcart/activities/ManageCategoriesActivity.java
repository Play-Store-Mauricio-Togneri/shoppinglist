package com.mauriciotogneri.shoppingcart.activities;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.ManageCategoriesView;

public class ManageCategoriesActivity extends BaseActivity<ManageCategoriesView> implements ManageCategoriesView.Observer
{
	public static final String RESULT_CATEGORY = "category";
	
	@Override
	protected void initialize()
	{
		this.view.initialize(this, this);
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
					this.view.editCategory(this, category, this);
					this.view.showToast(this, R.string.error_category_already_exists);
				}
			}
			else
			{
				if ((!category.getName().equals(name)) && Category.exists(name))
				{
					this.view.editCategory(this, category, this);
					this.view.showToast(this, R.string.error_category_already_exists);
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
			this.view.editCategory(this, category, this);
			this.view.showToast(this, R.string.error_invalid_name);
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
			this.view.showError(this);
		}
	}
	
	@Override
	public void onCategorySelected(Category category)
	{
		Intent intent = new Intent();
		intent.putExtra(ManageCategoriesActivity.RESULT_CATEGORY, category);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	@Override
	protected Class<ManageCategoriesView> getViewClass()
	{
		return ManageCategoriesView.class;
	}
}