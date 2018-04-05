package com.mauriciotogneri.shoppinglist.views;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView.ManageCategoriesViewObserver;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView.ViewContainer;

public class ManageCategoriesView extends BaseView<ManageCategoriesViewObserver, ViewContainer>
{
    public ManageCategoriesView(ManageCategoriesViewObserver observer)
    {
        super(R.layout.screen_manage_categories, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        toolbarTitle(R.string.toolbar_title_manage_categories);
        enableBack(v -> observer.onBack());
    }

    public interface ManageCategoriesViewObserver
    {
        void onBack();
    }

    public static class ViewContainer
    {
    }
}