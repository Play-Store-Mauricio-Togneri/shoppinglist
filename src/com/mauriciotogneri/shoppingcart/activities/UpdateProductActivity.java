package com.mauriciotogneri.shoppingcart.activities;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.SpinnerCategoryAdapter;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class UpdateProductActivity extends BaseActivity
{
	public static final String PARAMETER_PRODUCT_ID = "product_id";
	public static final String PARAMETER_CATEGORY = "category";
	
	private static final int SELECT_IMAGE_REQUEST = 123;
	private static final int SELECT_CATEGORY_REQUEST = 456;
	
	private Product product = null;
	private byte[] selectedImage = null;
	private SpinnerCategoryAdapter spinnerCategoryAdapter;
	
	@Override
	protected void init()
	{
		setContentView(R.layout.activity_update_product);
		
		long productId = getParameter(UpdateProductActivity.PARAMETER_PRODUCT_ID, 0L);
		Category initialCategory = getParameter(UpdateProductActivity.PARAMETER_CATEGORY, null);
		
		if (productId != 0)
		{
			this.product = Product.byId(productId);
		}
		
		initUI(this.product, initialCategory);
	}
	
	private void initUI(Product product, Category initialCategory)
	{
		TextView toolbarTitle = getCustomTextView(R.id.toolbar_title);
		
		if (product != null)
		{
			toolbarTitle.setText(R.string.toolbar_title_edit_product);
		}
		else
		{
			toolbarTitle.setText(R.string.toolbar_title_create_product);
		}
		
		// ---------------------------
		
		this.spinnerCategoryAdapter = new SpinnerCategoryAdapter(this);
		this.spinnerCategoryAdapter.refresh();
		
		Spinner productCategory = getSpinner(R.id.category);
		productCategory.setAdapter(this.spinnerCategoryAdapter);
		
		if (product != null)
		{
			productCategory.setSelection(this.spinnerCategoryAdapter.getPositionOf(product.getCategory()));
		}
		else if (initialCategory != null)
		{
			productCategory.setSelection(this.spinnerCategoryAdapter.getPositionOf(initialCategory));
		}
		
		// ---------------------------
		
		setButtonAction(R.id.button_manage_categories, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				manageCategories();
			}
		});
		
		// ---------------------------
		
		CustomEditText productName = getCustomEditText(R.id.name);
		
		if (product != null)
		{
			productName.setTextValue(product.getName());
		}
		
		// ---------------------------
		
		ProductImage productImage = getProductImage(R.id.thumbnail);
		productImage.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				updateImage();
			}
		});
		
		if (product != null)
		{
			setProductImage(product.getImage());
		}
		else
		{
			setProductImage(getImageFromBitmap(R.drawable.product_generic));
		}
		
		// ---------------------------
		
		TextView buttonUpdateText = getCustomTextView(R.id.button_update_text);
		
		if (product != null)
		{
			buttonUpdateText.setText(R.string.button_edit);
		}
		else
		{
			buttonUpdateText.setText(R.string.button_create);
		}
		
		// ---------------------------
		
		setButtonAction(R.id.button_update, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				update();
			}
		});
	}
	
	private void setProductImage(byte[] image)
	{
		this.selectedImage = getImageFromBitmap(getResizedBitmap(image, Product.IMAGE_SIZE, Product.IMAGE_SIZE));
		
		ProductImage productImage = getProductImage(R.id.thumbnail);
		productImage.setImage(this.selectedImage);
	}
	
	private Category getProductCategory()
	{
		Spinner productCategory = getSpinner(R.id.category);
		
		return (Category)productCategory.getSelectedItem();
	}
	
	private String getProductName()
	{
		CustomEditText productName = getCustomEditText(R.id.name);
		
		return productName.getTextValue();
	}
	
	private void updateImage()
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, UpdateProductActivity.SELECT_IMAGE_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if ((requestCode == UpdateProductActivity.SELECT_IMAGE_REQUEST) && (resultCode == Activity.RESULT_OK))
		{
			try
			{
				byte[] image = getBytesFromUri(data.getData());
				setProductImage(image);
			}
			catch (Exception e)
			{
				showToast(R.string.error_invalid_image);
			}
		}
		else if (requestCode == UpdateProductActivity.SELECT_CATEGORY_REQUEST)
		{
			this.spinnerCategoryAdapter.refresh();
			
			if (resultCode == Activity.RESULT_OK)
			{
				Category category = (Category)data.getSerializableExtra(ManageCategoriesActivity.RESULT_CATEGORY);
				
				if (category != null)
				{
					Spinner productCategory = getSpinner(R.id.category);
					productCategory.setSelection(this.spinnerCategoryAdapter.getPositionOf(category));
				}
			}
		}
	}
	
	private void update()
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
	
	private boolean isFormValid()
	{
		boolean valid = false;
		
		removeInputNameError();
		
		if (getProductCategory() == null)
		{
			showToast(R.string.error_invalid_category);
		}
		else if (TextUtils.isEmpty(getProductName()))
		{
			setNameInputError(R.string.error_invalid_name);
		}
		else if (((this.product == null) || (!this.product.getName().equals(getProductName()))) && (Product.exists(getProductName(), getProductCategory())))
		{
			setNameInputError(R.string.error_product_already_exists);
		}
		else if (this.selectedImage == null)
		{
			showToast(R.string.error_invalid_image);
		}
		else
		{
			valid = true;
		}
		
		return valid;
	}
	
	private void setNameInputError(int textId)
	{
		CustomEditText productName = getCustomEditText(R.id.name);
		productName.setErrorText(getString(textId));
	}
	
	private void removeInputNameError()
	{
		CustomEditText productName = getCustomEditText(R.id.name);
		productName.removeErrorText();
	}
	
	private void editProduct(Product product)
	{
		String name = getProductName();
		Category category = getProductCategory();
		
		product.update(name, category, this.selectedImage);
		
		finish();
	}
	
	private void createProduct()
	{
		String name = getProductName();
		Category category = getProductCategory();
		
		Product product = new Product(name, category, this.selectedImage);
		product.save();
		
		finish();
	}
	
	private void manageCategories()
	{
		Intent intent = new Intent(this, ManageCategoriesActivity.class);
		startActivityForResult(intent, UpdateProductActivity.SELECT_CATEGORY_REQUEST);
	}
}