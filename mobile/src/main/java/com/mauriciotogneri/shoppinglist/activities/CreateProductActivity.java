package com.mauriciotogneri.shoppinglist.activities;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mauriciotogneri.androidutils.ToastMessage;
import com.mauriciotogneri.androidutils.intents.Intents;
import com.mauriciotogneri.androidutils.permissions.OnPermissionGranted;
import com.mauriciotogneri.androidutils.permissions.Permissions;
import com.mauriciotogneri.androidutils.permissions.PermissionsResult;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseActivity;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.tasks.category.LoadCategories;
import com.mauriciotogneri.shoppinglist.tasks.category.LoadCategories.OnCategoriesLoaded;
import com.mauriciotogneri.shoppinglist.tasks.product.CreateProduct;
import com.mauriciotogneri.shoppinglist.tasks.product.CreateProduct.OnProductsCreated;
import com.mauriciotogneri.shoppinglist.utils.ResourceUtils;
import com.mauriciotogneri.shoppinglist.views.CreateProductView;
import com.mauriciotogneri.shoppinglist.views.CreateProductView.CreateProductViewObserver;
import com.mauriciotogneri.shoppinglist.views.Dialogs;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CreateProductActivity extends BaseActivity<CreateProductView> implements CreateProductViewObserver, OnCategoriesLoaded, OnProductsCreated
{
    private static final String PARAM_PRODUCT = "product";

    private static final int CAMERA_PERMISSION = 1001;
    private static final int READ_DISK_PERMISSION = 1002;

    private static final int CAMERA_IMAGE_REQUEST_CODE = 2001;
    private static final int GALLERY_IMAGE_REQUEST_CODE = 2002;
    private static final int SEARCH_IMAGE_REQUEST_CODE = 2003;

    private Uri cameraUri;

    public static Intent intent(Context context, Product product)
    {
        Intent intent = new Intent(context, CreateProductActivity.class);
        intent.putExtra(PARAM_PRODUCT, product);

        return intent;
    }

    @Override
    protected void initialize()
    {
        Product product = parameter(PARAM_PRODUCT, null);
        view.initialize(product == null);
    }

    @Override
    public void onCategoriesLoaded(List<Category> categories)
    {
        Product product = parameter(PARAM_PRODUCT, null);

        view.load(categories, product);
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
        List<String> options = Arrays.asList(
                getString(R.string.label_source_camera),
                getString(R.string.label_source_gallery),
                getString(R.string.label_source_search)
        );

        Dialogs dialogs = new Dialogs(this);
        dialogs.options(getString(R.string.label_select_picture_source), options, option ->
        {
            switch (option)
            {
                case 0:
                    imageFromCamera();
                    break;

                case 1:
                    imageFromGallery();
                    break;

                case 2:
                    imageFromSearch();
                    break;
            }
        });
    }

    private void imageFromCamera()
    {
        Permissions permissions = new Permissions(this, this);
        permissions.request(CAMERA_PERMISSION, permission.CAMERA);
    }

    private void imageFromGallery()
    {
        Permissions permissions = new Permissions(this, this);
        permissions.request(READ_DISK_PERMISSION, permission.READ_EXTERNAL_STORAGE);
    }

    @OnPermissionGranted(requestCode = CAMERA_PERMISSION, permission = permission.CAMERA)
    public void onPermissionsCameraGranted()
    {
        try
        {
            cameraUri = ResourceUtils.uri(this);

            Intents.takePicture(this, cameraUri)
                    .requestCode(CAMERA_IMAGE_REQUEST_CODE)
                    .send(this);
        }
        catch (Exception e)
        {
            new ToastMessage(this).shortMessage(R.string.error_openingCamera);
        }
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
    public void onAction(String category, String name, String image, Boolean inCart)
    {
        view.clearError();

        if (TextUtils.isEmpty(name))
        {
            view.missingName();
        }
        else
        {
            Product product = parameter(PARAM_PRODUCT, null);

            if (product != null)
            {
                System.out.println();
            }
            else
            {
                CreateProduct task = new CreateProduct(this, new Product(category, name, image, inCart, false), this);
                task.execute();
            }
        }
    }

    @Override
    public void onProductsCreated(Boolean result)
    {
        if (result)
        {
            finish();
        }
        else
        {
            // TODO
            new ToastMessage(this).shortMessage("ERROR");
        }
    }

    private void processUriImage(Uri uri)
    {
        try
        {
            File file = ResourceUtils.fileFromUri(this, uri);
            view.image(file.getAbsolutePath());
        }
        catch (Exception e)
        {
            new ToastMessage(this).shortMessage(R.string.error_loadingImage);
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
            processUriImage(cameraUri);
        }
        else if ((requestCode == GALLERY_IMAGE_REQUEST_CODE) && (resultCode == Activity.RESULT_OK))
        {
            processUriImage(data.getData());
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
    protected void onResume()
    {
        super.onResume();

        LoadCategories task = new LoadCategories(this, this);
        task.execute();
    }

    @Override
    protected CreateProductView view()
    {
        return new CreateProductView(this);
    }
}