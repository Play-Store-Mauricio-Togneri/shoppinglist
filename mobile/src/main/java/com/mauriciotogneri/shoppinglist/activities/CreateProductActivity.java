package com.mauriciotogneri.shoppinglist.activities;

import android.content.Intent;
import android.widget.Toast;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.views.CreateProductView;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.CreateProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.Dialogs;

public class CreateProductActivity extends BaseActivity<CreateProductView> implements CreateProductViewObserver
{
    @Override
    protected void initialize()
    {
        // TODO
    }

    @Override
    public void onBack()
    {
        finish();
    }

    @Override
    public void onManageCategories()
    {
        Intent intent = new Intent(this, ManageCategoriesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onChangeImage()
    {
        String[] options = new String[3];
        options[0] = getString(R.string.label_source_camera);
        options[1] = getString(R.string.label_source_gallery);
        options[2] = "Search";

        Dialogs dialogs = new Dialogs(this);
        dialogs.options(getString(R.string.label_select_picture_source), options, option -> {
            if (option == 0)
            {
                imageFromCamera();
            }
            else if (option == 1)
            {
                imageFromGallery();
            }
            else if (option == 2)
            {
                imageFromSearch();
            }
        });
    }

    private void imageFromCamera()
    {
        Toast.makeText(this, "CAMERA", Toast.LENGTH_SHORT).show();
    }

    private void imageFromGallery()
    {
        Toast.makeText(this, "GALLERY", Toast.LENGTH_SHORT).show();
    }

    private void imageFromSearch()
    {
        Intent intent = SearchImageActivity.intent(this, view.name().trim());
        startActivity(intent);
    }

    @Override
    public void onAction()
    {
        Toast.makeText(this, "ACTION", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected CreateProductView view()
    {
        return new CreateProductView(this);
    }
}