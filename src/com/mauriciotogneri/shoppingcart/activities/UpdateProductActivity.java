package com.mauriciotogneri.shoppingcart.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
import com.activeandroid.Model;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.CategoryAdapter;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class UpdateProductActivity extends Activity
{
	public static final String PARAMETER_PRODUCT_ID = "product_id";
	private static final int SELECT_IMAGE_REQUEST = 123;
	
	private Product product = null;
	
	private byte[] selectedImage = null;
	
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
		
		List<Category> categories = Model.all(Category.class);
		CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
		Spinner productCategory = (Spinner)findViewById(R.id.category);
		productCategory.setAdapter(categoryAdapter);
		
		if (product != null)
		{
			productCategory.setSelection(categoryAdapter.getPosition(product.getCategory()));
		}
		
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
		
		if (getProductCategory() == null)
		{
			Toast.makeText(this, R.string.error_invalid_category, Toast.LENGTH_SHORT).show();
		}
		else if (TextUtils.isEmpty(getProductName()))
		{
			Toast.makeText(this, R.string.error_invalid_name, Toast.LENGTH_SHORT).show();
		}
		else if (this.selectedImage == null)
		{
			Toast.makeText(this, R.string.error_invalid_image, Toast.LENGTH_SHORT).show();
		}
		else
		{
			valid = true;
		}
		
		// TODO: CHECK IF THERE IS A PRODUCT WITH THE SAME NAME
		
		return valid;
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
}