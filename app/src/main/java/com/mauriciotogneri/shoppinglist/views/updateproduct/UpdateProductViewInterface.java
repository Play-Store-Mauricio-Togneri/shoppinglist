package com.mauriciotogneri.shoppinglist.views.updateproduct;

import android.content.Context;

import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.BaseViewInterface;

import java.util.List;

public interface UpdateProductViewInterface extends BaseViewInterface
{
    void initialize(Context context, Product product, Category initialCategory, List<Category> list, UpdateProductViewObserver observer);

    void setProductImage(byte[] image);

    void setNameInputError(Context context, int textId);

    void removeInputNameError();

    Category getProductCategory();

    String getProductName();

    void refreshCategories(List<Category> list);

    void setCategory(Category category);
}