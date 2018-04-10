package com.mauriciotogneri.shoppinglist.database;

import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.model.Category;

import java.util.ArrayList;
import java.util.List;

public class LoadCategories extends AsyncTask<Void, Void, List<String>>
{
    private final CategoryDao dao;
    private final OnCategoriesLoaded callback;

    public LoadCategories(Context context, OnCategoriesLoaded callback)
    {
        this.dao = CategoryDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected List<String> doInBackground(Void... voids)
    {
        List<String> categories = new ArrayList<>();

        for (Category category : dao.all())
        {
            if (!categories.contains(category.name()))
            {
                categories.add(category.name());
            }
        }

        return categories;
    }

    @Override
    protected void onPostExecute(List<String> categories)
    {
        callback.onCategoriesLoaded(categories);
    }

    public interface OnCategoriesLoaded
    {
        void onCategoriesLoaded(List<String> categories);
    }
}