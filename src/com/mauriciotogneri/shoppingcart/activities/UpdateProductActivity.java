package com.mauriciotogneri.shoppingcart.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.SpinnerCategoryAdapter;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class UpdateProductActivity extends Activity
{
	public static final String PARAMETER_PRODUCT_ID = "product_id";
	private static final int SELECT_IMAGE_REQUEST = 123;
	private static final int SELECT_CATEGORY_REQUEST = 456;
	
	private Product product = null;
	private byte[] selectedImage = null;
	private SpinnerCategoryAdapter spinnerCategoryAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_product);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		long productId = getIntent().getLongExtra(UpdateProductActivity.PARAMETER_PRODUCT_ID, 0);
		
		if (productId != 0)
		{
			this.product = Product.byId(productId);
		}
		
		init(this.product);
	}
	
	private void init(Product product)
	{
		TextView toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
		
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
		
		Spinner productCategory = (Spinner)findViewById(R.id.category);
		productCategory.setAdapter(this.spinnerCategoryAdapter);
		
		if (product != null)
		{
			productCategory.setSelection(this.spinnerCategoryAdapter.getPositionOf(product.getCategory()));
		}
		
		// ---------------------------
		
		TextView buttonManageCategories = (TextView)findViewById(R.id.button_manage_categories);
		buttonManageCategories.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				manageCategories();
			}
		});
		
		// ---------------------------
		
		EditText productName = (EditText)findViewById(R.id.name);
		
		if (product != null)
		{
			productName.setText(product.getName());
			productName.setSelection(product.getName().length());
		}
		
		// ---------------------------
		
		ProductImage productImage = (ProductImage)findViewById(R.id.thumbnail);
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
			setProductImage(getImageFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.product_generic)));
		}
		
		// ---------------------------
		
		TextView buttonUpdateText = (TextView)findViewById(R.id.button_update_text);
		
		if (product != null)
		{
			buttonUpdateText.setText(R.string.button_edit);
		}
		else
		{
			buttonUpdateText.setText(R.string.button_create);
		}
		
		// ---------------------------
		
		LinearLayout buttonUpdate = (LinearLayout)findViewById(R.id.button_update);
		buttonUpdate.setOnClickListener(new View.OnClickListener()
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
		Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
		
		if ((bitmap.getWidth() != Product.IMAGE_SIZE) || (bitmap.getHeight() != Product.IMAGE_SIZE))
		{
			bitmap = Bitmap.createScaledBitmap(bitmap, Product.IMAGE_SIZE, Product.IMAGE_SIZE, false);
		}
		
		this.selectedImage = getImageFromBitmap(bitmap);
		
		ProductImage productImage = (ProductImage)findViewById(R.id.thumbnail);
		productImage.setImage(this.selectedImage);
	}
	
	private byte[] getImageFromBitmap(Bitmap bitmap)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		
		return stream.toByteArray();
	}
	
	private Category getProductCategory()
	{
		Spinner productCategory = (Spinner)findViewById(R.id.category);
		
		return (Category)productCategory.getSelectedItem();
	}
	
	private String getProductName()
	{
		CustomEditText productName = (CustomEditText)findViewById(R.id.name);
		
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
				Uri imageUri = data.getData();
				byte[] image = getImage(imageUri);
				setProductImage(image);
			}
			catch (Exception e)
			{
				Toast.makeText(this, R.string.error_invalid_image, Toast.LENGTH_SHORT).show();
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
					Spinner productCategory = (Spinner)findViewById(R.id.category);
					productCategory.setSelection(this.spinnerCategoryAdapter.getPositionOf(category));
				}
			}
		}
	}
	
	private byte[] getImage(Uri uri) throws IOException
	{
		InputStream inputStream = getContentResolver().openInputStream(uri);
		byte[] inputData = getBytes(inputStream);
		
		inputStream.close();
		
		return inputData;
	}
	
	private byte[] getBytes(InputStream inputStream) throws IOException
	{
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		
		int read = 0;
		
		while ((read = inputStream.read(buffer)) != -1)
		{
			byteBuffer.write(buffer, 0, read);
		}
		
		return byteBuffer.toByteArray();
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
			Toast.makeText(this, R.string.error_invalid_category, Toast.LENGTH_SHORT).show();
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
			Toast.makeText(this, R.string.error_invalid_image, Toast.LENGTH_SHORT).show();
		}
		else
		{
			valid = true;
		}
		
		return valid;
	}
	
	private void setNameInputError(int textId)
	{
		CustomEditText productName = (CustomEditText)findViewById(R.id.name);
		productName.requestFocus();
		productName.setError(getString(textId));
	}
	
	private void removeInputNameError()
	{
		CustomEditText productName = (CustomEditText)findViewById(R.id.name);
		productName.setError(null);
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