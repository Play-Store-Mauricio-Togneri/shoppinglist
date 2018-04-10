package com.mauriciotogneri.shoppinglist.activities;

import com.mauriciotogneri.androidutils.ToastMessage;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.tasks.AddCategory;
import com.mauriciotogneri.shoppinglist.tasks.AddCategory.OnCategoryAdded;
import com.mauriciotogneri.shoppinglist.tasks.LoadCategories;
import com.mauriciotogneri.shoppinglist.tasks.LoadCategories.OnCategoriesLoaded;
import com.mauriciotogneri.shoppinglist.tasks.RenameCategory;
import com.mauriciotogneri.shoppinglist.views.Dialogs;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView.ManageCategoriesViewObserver;

import java.util.Arrays;
import java.util.List;

public class ManageCategoriesActivity extends BaseActivity<ManageCategoriesView> implements ManageCategoriesViewObserver, OnCategoriesLoaded, OnCategoryAdded
{
    @Override
    protected void initialize()
    {
        reloadCategories();
    }

    private void reloadCategories()
    {
        LoadCategories loader = new LoadCategories(this, this);
        loader.execute();
    }

    @Override
    public void onCategoriesLoaded(List<Category> categories)
    {
        view.updateList(categories);
    }

    @Override
    public void onCategorySelected(Category category)
    {
        List<String> options = Arrays.asList(
                getString(R.string.rename),
                getString(R.string.button_remove)
        );

        Dialogs dialogs = new Dialogs(this);
        dialogs.options(category.name(), options, option ->
        {
            if (option == 0)
            {
                requestCategoryName(category);
            }
            else if (option == 1)
            {
                confirmRemoveCategory(category);
            }
        });
    }

    private void requestCategoryName(Category category)
    {
        Dialogs dialogs = new Dialogs(this);
        dialogs.input(this, getString(R.string.label_product_edit_category), category.name(), input -> renameCategory(category, input));
    }

    private void renameCategory(Category category, String newName)
    {
        RenameCategory renameCategory = new RenameCategory(this, category, newName, this::reloadCategories);
        renameCategory.execute();
    }

    private void confirmRemoveCategory(Category category)
    {
        Dialogs dialogs = new Dialogs(this);
        dialogs.confirmation(category.name(), getString(R.string.confirmation_remove_category), () -> removeCategory(category.name()));
    }

    private void removeCategory(String category)
    {

    }

    @Override
    public void onAddCategory()
    {
        Dialogs dialogs = new Dialogs(this);
        dialogs.input(this, getString(R.string.label_product_new_category), "", this::addCategory);
    }

    private void addCategory(String name)
    {
        AddCategory addCategory = new AddCategory(this, new Category(name), this);
        addCategory.execute();
    }

    @Override
    public void onCategoryAdded(Boolean result)
    {
        if (result)
        {
            reloadCategories();
        }
        else
        {
            new ToastMessage(this).shortMessage(R.string.error_category_already_exists);
        }
    }

    @Override
    public void onBack()
    {
        finish();
    }

    @Override
    protected ManageCategoriesView view()
    {
        return new ManageCategoriesView(this, this);
    }
}