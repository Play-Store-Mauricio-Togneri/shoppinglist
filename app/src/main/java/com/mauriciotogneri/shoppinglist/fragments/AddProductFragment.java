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

        this.view.initialize(getContext(), getFragmentManager(), categoryList, this);
    }

    @Override
    public void onCreateProduct()
    {
        UpdateProductFragment fragment = UpdateProductFragment.getInstance(view.getSelectedCategory());
        startFragment(fragment);
    }

    @Override
    protected AddProductViewInterface getViewInstance()
    {
        return new AddProductView();
    }
}