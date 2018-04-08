package com.mauriciotogneri.shoppinglist.activities;

import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.database.LoadCategories;
import com.mauriciotogneri.shoppinglist.database.LoadCategories.OnCategoriesLoaded;
import com.mauriciotogneri.shoppinglist.views.Dialogs;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView.ManageCategoriesViewObserver;

import java.util.List;

public class ManageCategoriesActivity extends BaseActivity<ManageCategoriesView> implements ManageCategoriesViewObserver, OnCategoriesLoaded
{
    @Override
    protected void initialize()
    {
        LoadCategories loader = new LoadCategories(this, this);
        loader.execute();
    }

    @Override
    public void onCategoriesLoaded(List<String> categories)
    {
        view.updateList(categories);
    }

    @Override
    public void onCategorySelected(String category)
    {
        String[] options = new String[2];
        options[0] = "Rename"; // TODO
        options[1] = getString(R.string.button_remove);

        Dialogs dialogs = new Dialogs(this);
        dialogs.options(category, options, option -> {
            if (option == 0)
            {
                renameCategory(category);
            }
            else if (option == 1)
            {
                confirmRemoveCategory(category);
            }
        });
    }

    private void renameCategory(String category)
    {
        Dialogs dialogs = new Dialogs(this);
        dialogs.input(this, getString(R.string.label_product_new_category), category, input -> {
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        });
    }

    private void confirmRemoveCategory(String category)
    {
        Dialogs dialogs = new Dialogs(this);
        dialogs.confirmation(category, getString(R.string.confirmation_remove_category), () -> removeCategory(category));
    }

    private void removeCategory(String category)
    {

    }

    @Override
    public void onAddCategory()
    {
        Dialogs dialogs = new Dialogs(this);
        dialogs.input(this, getString(R.string.label_product_new_category), "", input -> {
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        });
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