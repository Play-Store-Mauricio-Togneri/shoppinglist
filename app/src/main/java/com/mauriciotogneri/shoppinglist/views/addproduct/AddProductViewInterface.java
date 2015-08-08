package com.mauriciotogneri.shoppinglist.views.addproduct;

import android.content.Context;

import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.BaseViewInterface;

import java.util.List;

public interface AddProductViewInterface extends BaseViewInterface
{
    void initialize(Context context, AddProductViewObserver observer);

    void showError(Context context);

    void refreshList(List<Product> list);

    void refreshCategories(List<Category> list);

    Category getSelectedCategory();

    void setCategory(Category category);
}