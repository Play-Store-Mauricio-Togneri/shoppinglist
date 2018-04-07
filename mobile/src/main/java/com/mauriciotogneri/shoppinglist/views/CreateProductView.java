package com.mauriciotogneri.shoppinglist.views;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CategoryAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.CreateProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.ViewContainer;

import java.util.List;

public class CreateProductView extends BaseView<CreateProductViewObserver, ViewContainer>
{
    private static final String DEFAULT_IMAGE = "https://i.imgur.com/ztA411S.png";

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

        ui.buttonAction.setOnClickListener(v -> observer.onAction());
    }

    public void initialize(List<String> categories, Product product)
    {
        CategoryAdapter adapter = new CategoryAdapter(context());
        adapter.addAll(categories);

        ui.category.setAdapter(adapter);

        if (product != null)
        {
            ui.category.setSelection(categories.indexOf(product.category()));
            ui.name.setText(product.name());
            image(product.image());
            ui.buttonAction.setText(R.string.button_edit);
        }
        else
        {
            image(DEFAULT_IMAGE);
            ui.buttonAction.setText(R.string.button_create);
        }
    }

    @OnClick(R.id.product_image)
    public void onProductImage()
    {
        observer.onChangeImage();
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

    public String name()
    {
        return ui.name.getText().toString();
    }

    public void image(String imageUrl)
    {
        Glide.with(context())
                .load(imageUrl)
                .into(ui.image);
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

        @BindView(R.id.product_image)
        public ImageView image;

        @BindView(R.id.button_action)
        public Button buttonAction;
    }
}