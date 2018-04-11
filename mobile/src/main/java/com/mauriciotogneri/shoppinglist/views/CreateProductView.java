package com.mauriciotogneri.shoppinglist.views;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.CreateProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.ViewContainer;

import java.util.List;

public class CreateProductView extends BaseView<CreateProductViewObserver, ViewContainer>
{
    private static final String DEFAULT_IMAGE = "https://i.imgur.com/ztA411S.png";

    private String selectedImage;

    public CreateProductView(CreateProductViewObserver observer)
    {
        super(R.layout.screen_create_product, observer, new ViewContainer());
    }

    public void initialize(boolean isNew)
    {
        enableBack(v -> observer.onBack());

        if (isNew)
        {
            toolbarTitle(R.string.toolbar_title_create_product);
            ui.buttonAction.setText(R.string.button_create);
            image(DEFAULT_IMAGE);
        }
        else
        {
            ui.inCart.setChecked(false);
            ui.inCart.setVisibility(View.GONE);

            toolbarTitle(R.string.toolbar_title_edit_product);
            ui.buttonAction.setText(R.string.button_update);
        }
    }

    public void load(List<Category> categories, Product product)
    {
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(context(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ui.category.setAdapter(adapter);

        if (product != null)
        {
            ui.category.setSelection(categories.indexOf(new Category(product.category())));
            ui.name.setText(product.name());
            image(product.image());
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

    @OnClick(R.id.button_action)
    public void onAction()
    {
        observer.onAction(category(), name(), selectedImage, ui.inCart.isChecked());
    }

    private String category()
    {
        Object element = ui.category.getSelectedItem();

        return (element != null) ? element.toString() : "";
    }

    public String name()
    {
        return ui.name.getText().toString();
    }

    public void image(String image)
    {
        selectedImage = image;

        Glide.with(context())
                .load(image)
                .into(ui.image);
    }

    public void clearError()
    {
        ui.nameHeader.setError("");
    }

    public void missingName()
    {
        ui.nameHeader.setError(context().getString(R.string.error_invalid_name));
    }

    public interface CreateProductViewObserver
    {
        void onBack();

        void onManageCategories();

        void onChangeImage();

        void onAction(String category, String name, String image, Boolean inCart);
    }

    public static class ViewContainer
    {
        @BindView(R.id.name_header)
        public TextInputLayout nameHeader;

        @BindView(R.id.name)
        public EditText name;

        @BindView(R.id.category)
        public Spinner category;

        @BindView(R.id.product_image)
        public ImageView image;

        @BindView(R.id.product_addToCard)
        public CheckBox inCart;

        @BindView(R.id.button_action)
        public Button buttonAction;
    }
}