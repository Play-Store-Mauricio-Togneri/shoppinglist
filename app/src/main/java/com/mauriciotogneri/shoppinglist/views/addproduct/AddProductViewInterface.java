package com.mauriciotogneri.shoppinglist.views.addproduct;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.BaseViewInterface;

import java.util.List;

public interface AddProductViewInterface extends BaseViewInterface
{
    void initialize(Context context, FragmentManager fragmentManager, List<Category> categoryList, AddProductViewObserver observer);

    Category getSelectedCategory();
}