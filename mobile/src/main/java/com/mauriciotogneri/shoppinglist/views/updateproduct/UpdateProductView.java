package com.mauriciotogneri.shoppinglist.views.updateproduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.common.widgets.CustomEditText;
import com.mauriciotogneri.common.widgets.CustomImageView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.MenuItemAdapter;
import com.mauriciotogneri.shoppinglist.adapters.MenuItemAdapter.Option;
import com.mauriciotogneri.shoppinglist.adapters.SpinnerCategoryAdapter;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogMenu;
import com.mauriciotogneri.shoppinglist.views.updateproduct.UpdateProductView.UiContainer;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductView extends BaseView<UiContainer> implements UpdateProductViewInterface<UiContainer>
{
    private SpinnerCategoryAdapter spinnerCategoryAdapter;

    @Override
    public void initialize(final Context context, Product product, Category initialCategory, List<Category> list, final UpdateProductViewObserver observer)
    {
        if (product != null)
        {
            ui.toolbarTitle.setText(R.string.toolbar_title_edit_product);
        }
        else
        {
            ui.toolbarTitle.setText(R.string.toolbar_title_create_product);
        }

        // ---------------------------

        spinnerCategoryAdapter = new SpinnerCategoryAdapter(context);
        spinnerCategoryAdapter.refresh(list);

        ui.productCategory.setAdapter(spinnerCategoryAdapter);

        if (product != null)
        {
            ui.productCategory.setSelection(spinnerCategoryAdapter.getPositionOf(product.getCategory()));
        }
        else if (initialCategory != null)
        {
            ui.productCategory.setSelection(spinnerCategoryAdapter.getPositionOf(initialCategory));
        }

        // ---------------------------

        ui.buttonManageCategories.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onManageCategories();
            }
        });

        // ---------------------------

        if (product != null)
        {
            ui.productName.setTextValue(product.getName());
        }

        // ---------------------------

        ui.productImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chooseImageSource(context, observer);
            }
        });

        // ---------------------------

        if (product != null)
        {
            ui.buttonUpdateText.setText(R.string.button_edit);
        }
        else
        {
            ui.buttonUpdateText.setText(R.string.button_create);
        }

        // ---------------------------

        ui.buttonUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onUpdateProduct();
            }
        });
    }

    private void chooseImageSource(Context context, final UpdateProductViewObserver observer)
    {
        final int SOURCE_GALLERY = 0;
        final int SOURCE_CAMERA = 1;

        List<Option> optionsList = new ArrayList<>();
        optionsList.add(new Option(context.getString(R.string.icon_gallery), context.getString(R.string.label_source_gallery)));
        optionsList.add(new Option(context.getString(R.string.icon_camera), context.getString(R.string.label_source_camera)));

        DialogMenu dialog = new DialogMenu(context, context.getString(R.string.label_select_picture_source));

        ListAdapter menuItemAdapter = new MenuItemAdapter(context, optionsList);
        dialog.setAdapter(menuItemAdapter, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int index)
            {
                switch (index)
                {
                    case SOURCE_GALLERY:
                        observer.onUpdateImageGallery();
                        break;

                    case SOURCE_CAMERA:
                        observer.onUpdateImageCamera();
                        break;
                }
            }
        });

        dialog.display();
    }

    @Override
    public void setProductImage(byte[] image)
    {
        ui.productImage.setImage(image);
    }

    @Override
    public void setNameInputError(Context context, int textId)
    {
        ui.productName.setErrorText(context.getString(textId));
    }

    @Override
    public void removeInputNameError()
    {
        ui.productName.removeErrorText();
    }

    @Override
    public Category getProductCategory()
    {
        return (Category) ui.productCategory.getSelectedItem();
    }

    @Override
    public String getProductName()
    {
        return ui.productName.getTextValue();
    }

    @Override
    public void refreshCategories(List<Category> list)
    {
        spinnerCategoryAdapter.refresh(list);
    }

    @Override
    public void setCategory(Category category)
    {
        ui.productCategory.setSelection(spinnerCategoryAdapter.getPositionOf(category));
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_update_product;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private final TextView toolbarTitle;
        private final Spinner productCategory;
        private final TextView buttonManageCategories;
        private final TextView buttonUpdateText;
        private final View buttonUpdate;
        private final CustomEditText productName;
        private final CustomImageView productImage;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            this.productCategory = (Spinner) findViewById(R.id.category);
            this.buttonManageCategories = (TextView) findViewById(R.id.button_manage_categories);
            this.buttonUpdateText = (TextView) findViewById(R.id.button_update_text);
            this.buttonUpdate = findViewById(R.id.button_update);
            this.productName = (CustomEditText) findViewById(R.id.name);
            this.productImage = (CustomImageView) findViewById(R.id.thumbnail);
        }
    }
}