package com.mauriciotogneri.shoppinglist.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.database.CategoryDao;
import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Category;

public class RenameCategory extends AsyncTask<Void, Void, Void>
{
    private final Category category;
    private final String newName;
    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final OnCategoryRenamed callback;

    public RenameCategory(Context context, Category category, String newName, OnCategoryRenamed callback)
    {
        this.category = category;
        this.newName = newName;
        this.categoryDao = CategoryDao.instance(context);
        this.productDao = ProductDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        categoryDao.rename(category.id(), newName);
        productDao.rename(category.name(), newName);

        return null;
    }

    @Override
    protected void onPostExecute(Void avoid)
    {
        callback.onCategoryRenamed();
    }

    public interface OnCategoryRenamed
    {
        void onCategoryRenamed();
    }
}