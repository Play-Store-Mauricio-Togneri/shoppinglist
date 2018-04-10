package com.mauriciotogneri.shoppinglist.tasks.category;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.database.CategoryDao;
import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Category;

public class RenameCategory extends AsyncTask<Void, Void, Boolean>
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
    protected Boolean doInBackground(Void... voids)
    {
        Boolean result = false;

        if (!categoryDao.contains(newName))
        {
            categoryDao.rename(category.name(), newName);
            productDao.rename(category.name(), newName);
            result = true;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        callback.onCategoryRenamed(result);
    }

    public interface OnCategoryRenamed
    {
        void onCategoryRenamed(Boolean result);
    }
}