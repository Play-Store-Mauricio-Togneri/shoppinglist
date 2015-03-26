package com.mauriciotogneri.shoppingcart.fragments;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.dao.CategoryDao;
import com.mauriciotogneri.shoppingcart.dao.ProductDao;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.updateproduct.UpdateProductView;
import com.mauriciotogneri.shoppingcart.views.updateproduct.UpdateProductViewInterface;
import com.mauriciotogneri.shoppingcart.views.updateproduct.UpdateProductViewObserver;

public class UpdateProductFragment extends BaseFragment<UpdateProductViewInterface> implements UpdateProductViewObserver
{
	public static final String PARAMETER_PRODUCT_ID = "product_id";
	public static final String PARAMETER_CATEGORY = "category";
	
	private static final int SELECT_IMAGE_GALERY = 123;
	private static final int SELECT_IMAGE_CAMERA = 456;
	
	private Product product = null;
	private byte[] selectedImage = null;
	
	@Override
	protected void initialize()
	{
		long productId = getParameter(UpdateProductFragment.PARAMETER_PRODUCT_ID, 0L);
		Category initialCategory = getParameter(UpdateProductFragment.PARAMETER_CATEGORY, null);
		
		if (productId != 0)
		{
			ProductDao productDao = new ProductDao();
			this.product = productDao.byId(productId);
		}
		
		if (this.product != null)
		{
			setProductImage(this.product.getImage());
		}
		else
		{
			setProductImage(getImageFromBitmap(R.drawable.product_generic));
		}
		
		List<Category> list = getCategories();
		
		this.view.initialize(getContext(), this.product, initialCategory, list, this);
	}
	
	private void setProductImage(byte[] image)
	{
		Bitmap genericBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.product_generic);
		
		this.selectedImage = getImageFromBitmap(getResizedBitmap(image, genericBitmap.getWidth(), genericBitmap.getHeight()));
		this.view.setProductImage(this.selectedImage);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if ((requestCode == UpdateProductFragment.SELECT_IMAGE_GALERY) && (resultCode == Activity.RESULT_OK))
		{
			try
			{
				byte[] image = getBytesFromUri(data.getData());
				setProductImage(image);
			}
			catch (Exception e)
			{
				this.view.showToast(getContext(), R.string.error_invalid_image);
			}
		}
		else if ((requestCode == UpdateProductFragment.SELECT_IMAGE_CAMERA) && (resultCode == Activity.RESULT_OK))
		{
			try
			{
				Bundle extras = data.getExtras();
				Bitmap bitmap = (Bitmap)extras.get("data");
				byte[] image = getImageFromBitmap(bitmap);
				setProductImage(image);
			}
			catch (Exception e)
			{
				this.view.showToast(getContext(), R.string.error_invalid_image);
			}
		}
	}
	
	@Override
	public void onUpdateProduct()
	{
		if (isFormValid())
		{
			if (this.product != null)
			{
				editProduct(this.product);
			}
			else
			{
				createProduct();
			}
		}
	}
	
	private void editProduct(Product product)
	{
		String name = this.view.getProductName();
		Category category = this.view.getProductCategory();
		
		product.update(name, category, this.selectedImage);
		
		close();
	}
	
	private void createProduct()
	{
		String name = this.view.getProductName();
		Category category = this.view.getProductCategory();
		
		Product product = new Product(name, category, this.selectedImage);
		product.save();
		
		close();
	}
	
	private boolean isFormValid()
	{
		boolean valid = false;
		
		this.view.removeInputNameError();
		
		String name = this.view.getProductName();
		Category category = this.view.getProductCategory();
		
		ProductDao productDao = new ProductDao();
		
		if (category == null)
		{
			this.view.showToast(getContext(), R.string.error_invalid_category);
		}
		else if (TextUtils.isEmpty(name))
		{
			this.view.setNameInputError(getContext(), R.string.error_invalid_name);
		}
		else if (((this.product == null) || (!this.product.getName().equals(name))) && (productDao.exists(name, category)))
		{
			this.view.setNameInputError(getContext(), R.string.error_product_already_exists);
		}
		else if (this.selectedImage == null)
		{
			this.view.showToast(getContext(), R.string.error_invalid_image);
		}
		else
		{
			valid = true;
		}
		
		return valid;
	}
	
	@Override
	public void onUpdateImageGalery()
	{
		this.view.removeInputNameError();
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, UpdateProductFragment.SELECT_IMAGE_GALERY);
	}
	
	@Override
	public void onUpdateImageCamera()
	{
		this.view.removeInputNameError();
		
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		if (takePictureIntent.resolveActivity(this.activity.getPackageManager()) != null)
		{
			startActivityForResult(takePictureIntent, UpdateProductFragment.SELECT_IMAGE_CAMERA);
		}
	}
	
	@Override
	public void onManageCategories()
	{
		this.view.removeInputNameError();
		
		ManageCategoriesFragment fragment = new ManageCategoriesFragment();
		startFragment(fragment);
	}
	
	@Override
	public void onActivate(Object result)
	{
		List<Category> list = getCategories();
		
		this.view.refreshCategories(list);
		
		Category category = (Category)result;
		
		if (category != null)
		{
			this.view.setCategory(category);
		}
	}
	
	private List<Category> getCategories()
	{
		CategoryDao categoryDao = new CategoryDao();
		
		return categoryDao.getCategories();
	}
	
	@Override
	public void onCategorySelected(Category category)
	{
		setResult(category);
	}
	
	@Override
	protected UpdateProductView getViewInstance()
	{
		return new UpdateProductView();
	}
}