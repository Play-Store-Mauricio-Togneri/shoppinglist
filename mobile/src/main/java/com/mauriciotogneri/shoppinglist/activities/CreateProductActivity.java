package com.mauriciotogneri.shoppinglist.activities;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.mauriciotogneri.androidutils.intents.Intents;
import com.mauriciotogneri.androidutils.permissions.OnPermissionGranted;
import com.mauriciotogneri.androidutils.permissions.Permissions;
import com.mauriciotogneri.androidutils.permissions.PermissionsResult;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.utils.ResoursesUtils;
import com.mauriciotogneri.shoppinglist.views.CreateProductView;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.CreateProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.Dialogs;

import java.io.File;

public class CreateProductActivity extends BaseActivity<CreateProductView> implements CreateProductViewObserver
{
    private static final int READ_DISK_PERMISSION = 1001;

    private static final int CAMERA_IMAGE_REQUEST_CODE = 2001;
    private static final int GALLERY_IMAGE_REQUEST_CODE = 2002;
    private static final int SEARCH_IMAGE_REQUEST_CODE = 2003;

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
        Permissions permissions = new Permissions(this, this);
        permissions.request(READ_DISK_PERMISSION, permission.READ_EXTERNAL_STORAGE);
    }

    @OnPermissionGranted(requestCode = READ_DISK_PERMISSION, permission = permission.READ_EXTERNAL_STORAGE)
    public void onPermissionsReadStorageGranted()
    {
        Intents.selectFromGallery("image/*")
                .requestCode(GALLERY_IMAGE_REQUEST_CODE)
                .send(this);
    }

    private void imageFromSearch()
    {
        Intent intent = SearchImageActivity.intent(this, view.name().trim());
        startActivityForResult(intent, SEARCH_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onAction()
    {
        Toast.makeText(this, "ACTION", Toast.LENGTH_SHORT).show();
    }

    private void processCameraImage(Uri uri)
    {
        // TODO
    }

    private void processGalleryImage(Uri uri)
    {
        try
        {
            File file = ResoursesUtils.fileFromUri(this, uri);
            view.image(file.getAbsolutePath());
        }
        catch (Exception e)
        {
            // TODO
            Toast.makeText(this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void processSearchImage(String imageUrl)
    {
        view.image(imageUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == CAMERA_IMAGE_REQUEST_CODE) && (resultCode == Activity.RESULT_OK))
        {
            processCameraImage(data.getData());
        }
        else if ((requestCode == GALLERY_IMAGE_REQUEST_CODE) && (resultCode == Activity.RESULT_OK))
        {
            processGalleryImage(data.getData());
        }
        else if ((requestCode == SEARCH_IMAGE_REQUEST_CODE) && (resultCode == Activity.RESULT_OK))
        {
            processSearchImage(data.getStringExtra(SearchImageActivity.PARAM_IMAGE_URL));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionsResult permissionsResult = new PermissionsResult(this);
        permissionsResult.process(requestCode, permissions, grantResults);
    }

    @Override
    protected CreateProductView view()
    {
        return new CreateProductView(this);
    }
}