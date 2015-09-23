package com.mauriciotogneri.shoppinglist.views.addproduct;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;
import com.mauriciotogneri.shoppinglist.model.Category;

import java.util.List;

public interface AddProductViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(Context context, FragmentManager fragmentManager, List<Category> categoryList, int selected, AddProductViewObserver observer);

    Category getSelectedCategory();
}