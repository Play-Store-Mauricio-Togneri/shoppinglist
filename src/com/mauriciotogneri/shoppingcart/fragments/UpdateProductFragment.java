package com.mauriciotogneri.shoppingcart.fragments;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.views.UpdateProductView;

public class UpdateProductFragment extends BaseFragment<UpdateProductView> implements UpdateProductView.Observer
{
	public static final String PARAMETER_PRODUCT_ID = "product_id";
	public static final String PARAMETER_CATEGORY = "category";
	
	private static final int SELECT_IMAGE_REQUEST = 123;
	
	private Product product = null;
	private byte[] selectedImage = null;
	
	@Override
	protected void initialize()
	{
		long productId = getParameter(UpdateProductFragment.PARAMETER_PRODUCT_ID, 0L);
		Category initialCategory = getParameter(UpdateProductFragment.PARAMETER_CATEGORY, null);
		
		if (productId != 0)
		{
			this.product = Product.byId(productId);
		}
		
		if (this.product != null)
		{
			setProductImage(this.product.getImage());
		}
		else
		{
			setProductImage(getImageFromBitmap(R.drawable.product_generic));
		}
		
		this.view.initialize(getContext(), this.product, initialCategory, this);
	}
	
	private void setProductImage(byte[] image)
	{
		this.selectedImage = getImageFromBitmap(getResizedBitmap(image, Product.IMAGE_SIZE, Product.IMAGE_SIZE));
		this.view.setProductImage(this.selectedImage);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if ((requestCode == UpdateProductFragment.SELECT_IMAGE_REQUEST) && (resultCode == Activity.RESULT_OK))
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
		
		if (category == null)
		{
			this.view.showToast(getContext(), R.string.error_invalid_category);
		}
		else if (TextUtils.isEmpty(name))
		{
			this.view.setNameInputError(getContext(), R.string.error_invalid_name);
		}
		else if (((this.product == null) || (!this.product.getName().equals(name))) && (Product.exists(name, category)))
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
	public void onUpdateImage()
	{
		this.view.removeInputNameError();
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, UpdateProductFragment.SELECT_IMAGE_REQUEST);
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
		this.view.refreshCategories();
		
		Category category = (Category)result;
		
		if (category != null)
		{
			this.view.setCategory(category);
		}
	}
	
	@Override
	public void onCategorySelected(Category category)
	{
		setResult(category);
	}
	
	@Override
	protected Class<UpdateProductView> getViewClass()
	{
		return UpdateProductView.class;
	}
}