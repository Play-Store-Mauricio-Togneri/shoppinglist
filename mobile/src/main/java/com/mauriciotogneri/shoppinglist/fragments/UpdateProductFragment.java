package com.mauriciotogneri.shoppinglist.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.mauriciotogneri.common.base.BaseFragment;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.dao.CategoryDao;
import com.mauriciotogneri.shoppinglist.dao.ProductDao;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.common.utils.ImageHelper;
import com.mauriciotogneri.shoppinglist.views.updateproduct.UpdateProductView;
import com.mauriciotogneri.shoppinglist.views.updateproduct.UpdateProductViewInterface;
import com.mauriciotogneri.shoppinglist.views.updateproduct.UpdateProductViewObserver;

import java.util.List;

public class UpdateProductFragment extends BaseFragment<UpdateProductViewInterface> implements UpdateProductViewObserver
{
    private static final String PARAMETER_PRODUCT_ID = "product_id";
    private static final String PARAMETER_CATEGORY = "category";

    private static final int SELECT_IMAGE_GALLERY = 123;
    private static final int SELECT_IMAGE_CAMERA = 456;

    private Product product = null;
    private byte[] selectedImage = null;

    public static UpdateProductFragment getInstance(Category category)
    {
        UpdateProductFragment fragment = new UpdateProductFragment();
        Bundle parameters = new Bundle();
        parameters.putSerializable(UpdateProductFragment.PARAMETER_CATEGORY, category);
        fragment.setArguments(parameters);

        return fragment;
    }

    public static UpdateProductFragment getInstance(long productId)
    {
        UpdateProductFragment fragment = new UpdateProductFragment();
        Bundle parameters = new Bundle();
        parameters.putLong(UpdateProductFragment.PARAMETER_PRODUCT_ID, productId);
        fragment.setArguments(parameters);

        return fragment;
    }

    @Override
    protected void initialize()
    {
        long productId = getParameter(UpdateProductFragment.PARAMETER_PRODUCT_ID, 0L);
        Category initialCategory = getParameter(UpdateProductFragment.PARAMETER_CATEGORY, null);

        if (productId != 0)
        {
            ProductDao productDao = new ProductDao();
            product = productDao.byId(productId);
        }

        if (product != null)
        {
            setProductImage(product.getImage());
        }
        else
        {
            setProductImage(ImageHelper.getImageBytesFromResource(getContext(), R.drawable.product_generic));
        }

        List<Category> list = getCategories();

        view.initialize(getContext(), product, initialCategory, list, this);
    }

    private void setProductImage(byte[] image)
    {
        selectedImage = image;
        view.setProductImage(selectedImage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ((requestCode == UpdateProductFragment.SELECT_IMAGE_GALLERY) && (resultCode == Activity.RESULT_OK))
        {
            try
            {
                byte[] image = ImageHelper.getImageBytesFromUri(getContext(), data.getData());
                setProductImage(image);
            }
            catch (Exception e)
            {
                view.showToast(R.string.error_invalid_image);
            }
        }
        else if ((requestCode == UpdateProductFragment.SELECT_IMAGE_CAMERA) && (resultCode == Activity.RESULT_OK))
        {
            try
            {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                byte[] image = ImageHelper.getImageBytesFromBitmap(bitmap);
                setProductImage(image);
            }
            catch (Exception e)
            {
                view.showToast(R.string.error_invalid_image);
            }
        }
    }

    @Override
    public void onUpdateProduct()
    {
        if (isFormValid())
        {
            if (product != null)
            {
                editProduct(product);
            }
            else
            {
                createProduct();
            }
        }
    }

    private void editProduct(Product product)
    {
        String name = view.getProductName();
        Category category = view.getProductCategory();

        product.update(name, category, selectedImage);

        close();
    }

    private void createProduct()
    {
        String name = view.getProductName();
        Category category = view.getProductCategory();

        Product product = new Product(name, category, selectedImage);
        product.save();

        close();
    }

    private boolean isFormValid()
    {
        boolean valid = false;

        view.removeInputNameError();

        String name = view.getProductName();
        Category category = view.getProductCategory();

        ProductDao productDao = new ProductDao();

        if (category == null)
        {
            view.showToast(R.string.error_invalid_category);
        }
        else if (TextUtils.isEmpty(name))
        {
            view.setNameInputError(getContext(), R.string.error_invalid_name);
        }
        else if (((product == null) || (!product.getName().equals(name))) && (productDao.exists(name, category)))
        {
            view.setNameInputError(getContext(), R.string.error_product_already_exists);
        }
        else if (selectedImage == null)
        {
            view.showToast(R.string.error_invalid_image);
        }
        else
        {
            valid = true;
        }

        return valid;
    }

    @Override
    public void onUpdateImageGallery()
    {
        view.removeInputNameError();

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, UpdateProductFragment.SELECT_IMAGE_GALLERY);
    }

    @Override
    public void onUpdateImageCamera()
    {
        view.removeInputNameError();

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, UpdateProductFragment.SELECT_IMAGE_CAMERA);
        }
    }

    @Override
    public void onManageCategories()
    {
        view.removeInputNameError();

        ManageCategoriesFragment fragment = new ManageCategoriesFragment();
        startFragment(fragment);
    }

    @Override
    public void onActivate(Object result)
    {
        refreshCategories();

        Category category = (Category) result;

        if (category != null)
        {
            view.setCategory(category);
        }
    }

    @Override
    public void onActivate()
    {
        refreshCategories();
    }

    private void refreshCategories()
    {
        List<Category> list = getCategories();
        view.fillCategories(list);
    }

    private List<Category> getCategories()
    {
        CategoryDao categoryDao = new CategoryDao();

        return categoryDao.getCategories();
    }

    @Override
    protected UpdateProductViewInterface getViewInstance()
    {
        return new UpdateProductView();
    }
}