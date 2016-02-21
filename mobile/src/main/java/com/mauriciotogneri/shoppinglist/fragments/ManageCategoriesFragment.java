package com.mauriciotogneri.shoppinglist.fragments;

import android.text.TextUtils;

import com.mauriciotogneri.common.base.BaseFragment;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.dao.CategoryDao;
import com.mauriciotogneri.shoppinglist.dao.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.managecategories.ManageCategoriesView;
import com.mauriciotogneri.shoppinglist.views.managecategories.ManageCategoriesViewInterface;
import com.mauriciotogneri.shoppinglist.views.managecategories.ManageCategoriesViewObserver;

import java.util.List;

public class ManageCategoriesFragment extends BaseFragment<ManageCategoriesViewInterface> implements ManageCategoriesViewObserver
{
    @Override
    protected void initialize()
    {
        List<Category> list = getCategories();

        view.initialize(getContext(), list, this);
    }

    @Override
    public boolean onEditCategory(Category category, String name, String color)
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

                    return true;
                }
                else
                {
                    view.showToast(R.string.error_category_already_exists);
                }
            }
            else
            {
                CategoryDao categoryDao = new CategoryDao();

                if ((!TextUtils.equals(category.getName(), name)) && categoryDao.exists(name))
                {
                    view.showToast(R.string.error_category_already_exists);
                }
                else
                {
                    category.update(name, color);
                    refreshList();

                    return true;
                }
            }
        }
        else
        {
            view.showToast(R.string.error_invalid_name);
        }

        return false;
    }

    @Override
    public void onRemoveCategory(Category category)
    {
        ProductDao productDao = new ProductDao();
        CategoryDao categoryDao = new CategoryDao();

        if ((!productDao.exists(category)) && (categoryDao.getNumberOfCategories() > 1))
        {
            category.delete();
            refreshList();
        }
        else
        {
            view.showError();
        }
    }

    private void refreshList()
    {
        List<Category> list = getCategories();

        view.fillList(list);
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
    protected ManageCategoriesViewInterface getViewInstance()
    {
        return new ManageCategoriesView();
    }
}