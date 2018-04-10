package com.mauriciotogneri.shoppinglist.tasks.category;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.database.CategoryDao;
import com.mauriciotogneri.shoppinglist.model.Category;

public class AddCategory extends AsyncTask<Void, Void, Boolean>
{
    private final Category category;
    private final CategoryDao dao;
    private final OnCategoryAdded callback;

    public AddCategory(Context context, Category category, OnCategoryAdded callback)
    {
        this.category = category;
        this.dao = CategoryDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        Boolean result = false;

        if (!dao.all().contains(category))
        {
            dao.insert(category);
            result = true;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        callback.onCategoryAdded(result);
    }

    public interface OnCategoryAdded
    {
        void onCategoryAdded(Boolean result);
    }
}