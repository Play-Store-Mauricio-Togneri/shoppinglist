package com.mauriciotogneri.shoppinglist.views.updateproduct;

import com.mauriciotogneri.shoppinglist.model.Category;

public interface UpdateProductViewObserver
{
    void onManageCategories();

    void onUpdateImageGallery();

    void onUpdateImageCamera();

    void onUpdateProduct();

    void onCategorySelected(Category category);
}