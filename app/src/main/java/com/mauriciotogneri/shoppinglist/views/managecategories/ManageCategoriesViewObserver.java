package com.mauriciotogneri.shoppinglist.views.managecategories;

import com.mauriciotogneri.shoppinglist.model.Category;

public interface ManageCategoriesViewObserver
{
    void onCategorySelected(Category category);

    void onEditCategory(Category category, String name, String color);

    void onRemoveCategory(Category category);
}