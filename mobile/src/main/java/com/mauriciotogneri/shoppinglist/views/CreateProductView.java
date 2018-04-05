package com.mauriciotogneri.shoppinglist.views;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CategoryAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.CreateProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.ViewContainer;

public class CreateProductView extends BaseView<CreateProductViewObserver, ViewContainer>
{
    public CreateProductView(CreateProductViewObserver observer)
    {
        super(R.layout.screen_create_product, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        // TODO: create or update title
        toolbarTitle(R.string.toolbar_title_create_product);
        enableBack(v -> observer.onBack());

        CategoryAdapter adapter = new CategoryAdapter(context());
        adapter.add("Category 1");
        adapter.add("Category 2");
        adapter.add("Category 3");
        adapter.add("Category 4");
        adapter.add("Category 5");

        ui.category.setAdapter(adapter);
        ui.category.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                String category = (String) adapterView.getItemAtPosition(position);
                System.out.println(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });

        ui.buttonAction.setOnClickListener(v -> observer.onAction());
    }

    @OnClick(R.id.product_image_change)
    public void onChangeImage()
    {
        observer.onChangeImage();
    }

    @OnClick(R.id.category_manage)
    public void onManageCategories()
    {
        observer.onManageCategories();
    }

    public interface CreateProductViewObserver
    {
        void onBack();

        void onManageCategories();

        void onChangeImage();

        void onAction();
    }

    public static class ViewContainer
    {
        @BindView(R.id.name)
        public EditText name;

        @BindView(R.id.category)
        public Spinner category;

        @BindView(R.id.button_action)
        public View buttonAction;
    }
}