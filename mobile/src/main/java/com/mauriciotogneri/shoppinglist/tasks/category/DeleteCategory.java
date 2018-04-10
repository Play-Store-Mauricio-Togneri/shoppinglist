package com.mauriciotogneri.shoppinglist.tasks.category;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.database.CategoryDao;
import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Category;

public class DeleteCategory extends AsyncTask<Void, Void, Boolean>
{
    private final Category category;
    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final OnCategoryDeleted callback;

    public DeleteCategory(Context context, Category category, OnCategoryDeleted callback)
    {
        this.category = category;
        this.categoryDao = CategoryDao.instance(context);
        this.productDao = ProductDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        Boolean result = false;

        if (productDao.byCategory(category.name()).isEmpty())
        {
            categoryDao.delete(category);
            result = true;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        callback.onCategoryDeleted(result);
    }

    public interface OnCategoryDeleted
    {
        void onCategoryDeleted(Boolean result);
    }
}