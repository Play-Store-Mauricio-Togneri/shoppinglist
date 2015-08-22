package com.mauriciotogneri.shoppinglist.fragments;

import com.mauriciotogneri.shoppinglist.dao.CategoryDao;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.addproduct.AddProductView;
import com.mauriciotogneri.shoppinglist.views.addproduct.AddProductViewInterface;
import com.mauriciotogneri.shoppinglist.views.addproduct.AddProductViewObserver;

import java.util.List;

public class AddProductFragment extends BaseFragment<AddProductViewInterface> implements AddProductViewObserver
{
    @Override
    protected void initialize()
    {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getCategories();

        this.view.initialize(getContext(), getChildFragmentManager(), categoryList, 0, this);
    }

    @Override
    public void onCreateProduct()
    {
        UpdateProductFragment fragment = UpdateProductFragment.getInstance(view.getSelectedCategory());
        startFragment(fragment);
    }

    @Override
    public void onActivate()
    {
        Category category = view.getSelectedCategory();

        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getCategories();

        view.initialize(getContext(), getChildFragmentManager(), categoryList, getCategoryIndex(category, categoryList), this);
    }

    private int getCategoryIndex(Category category, List<Category> categoryList)
    {
        for (int i = 0; i < categoryList.size(); i++)
        {
            Category current = categoryList.get(i);

            if (current.getName().equals(category.getName()))
            {
                return i;
            }
        }

        return 0;
    }

    @Override
    protected AddProductViewInterface getViewInstance()
    {
        return new AddProductView();
    }
}