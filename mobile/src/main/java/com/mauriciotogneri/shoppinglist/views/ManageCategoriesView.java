package com.mauriciotogneri.shoppinglist.views;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CategoryAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView.ManageCategoriesViewObserver;
import com.mauriciotogneri.shoppinglist.views.ManageCategoriesView.ViewContainer;

import java.util.List;

public class ManageCategoriesView extends BaseView<ManageCategoriesViewObserver, ViewContainer>
{
    private final CategoryAdapter adapter;

    public ManageCategoriesView(Context context, ManageCategoriesViewObserver observer)
    {
        super(R.layout.screen_manage_categories, observer, new ViewContainer());

        this.adapter = new CategoryAdapter(context);
    }

    @Override
    protected void initialize()
    {
        toolbarTitle(R.string.toolbar_title_manage_categories);
        enableBack(v -> observer.onBack());

        // TODO ui.buttonAdd.setOnClickListener(v -> observer.onAddCategory());

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener((adapterView, view, position, id) -> {
            String product = (String) adapterView.getItemAtPosition(position);
            observer.onCategorySelected(product);
        });
    }

    public void updateList(List<String> categories)
    {
        if (!categories.isEmpty())
        {
            ui.labelEmpty.setVisibility(View.VISIBLE);
            ui.list.setVisibility(View.GONE);
        }
        else
        {
            ui.labelEmpty.setVisibility(View.GONE);
            ui.list.setVisibility(View.VISIBLE);
            adapter.set(categories);
        }
    }

    @OnClick(R.id.category_add)
    public void onAddCategory()
    {
        observer.onAddCategory();
    }

    public interface ManageCategoriesViewObserver
    {
        void onCategorySelected(String category);

        void onAddCategory();

        void onBack();
    }

    public static class ViewContainer
    {
        @BindView(R.id.category_list)
        public ListView list;

        @BindView(R.id.label_empty)
        public View labelEmpty;
    }
}