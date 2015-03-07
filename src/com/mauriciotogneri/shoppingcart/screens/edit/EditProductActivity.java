package com.mauriciotogneri.shoppingcart.screens.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.database.ProductDao;
import com.mauriciotogneri.shoppingcart.objects.Product;
import com.mauriciotogneri.shoppingcart.objects.Product.Category;
import com.mauriciotogneri.shoppingcart.objects.Product.Type;

public class EditProductActivity extends Activity
{
	private Product product;
	private ArrayAdapter<Category> categoryAdapter;
	
	private Type pendingSelectedType = null;
	
	public static String PARAMETER_PRODUCT = "product";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		this.product = (Product)getIntent().getSerializableExtra(EditProductActivity.PARAMETER_PRODUCT);
		
		Spinner category = (Spinner)findViewById(R.id.category);
		this.categoryAdapter = new ArrayAdapter<Category>(this, R.layout.spinner_type_row_normal, Category.values());
		this.categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(this.categoryAdapter);
		category.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				Category category = (Category)parent.getItemAtPosition(position);
				categorySelected(category);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		
		if (this.product == null)
		{
			setTitle("   " + getString(R.string.create_product_title));
		}
		else
		{
			fillFields(this.product);
			setTitle("   " + getString(R.string.edit_product_title));
		}
	}
	
	private void fillFields(Product product)
	{
		Category category = Category.getCategoryFromType(product.getType());
		
		if (category != null)
		{
			this.pendingSelectedType = product.getType();
			
			Spinner categoryField = (Spinner)findViewById(R.id.category);
			int categoryPosition = this.categoryAdapter.getPosition(category);
			categoryField.setSelection(categoryPosition);
		}
		
		EditText nameField = (EditText)findViewById(R.id.name);
		nameField.setText(this.product.getName());
	}
	
	private void categorySelected(Category category)
	{
		Spinner typeField = (Spinner)findViewById(R.id.type);
		
		ArrayAdapter<Type> typeAdapter = new ArrayAdapter<Type>(this, R.layout.spinner_type_row_normal, category.getTypes())
		{
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent)
			{
				Type type = getItem(position);
				
				LayoutInflater inflater = LayoutInflater.from(EditProductActivity.this);
				View layout = inflater.inflate(R.layout.spinner_type_row_dropdown, parent, false);
				
				TextView textView = (TextView)layout.findViewById(R.id.text);
				textView.setText(type.toString());
				textView.setCompoundDrawablesWithIntrinsicBounds(type.getThumbnail(), 0, 0, 0);
				
				return layout;
			}
		};
		
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeField.setAdapter(typeAdapter);
		
		if (this.pendingSelectedType != null)
		{
			int typePosition = typeAdapter.getPosition(this.pendingSelectedType);
			typeField.setSelection(typePosition);
			
			this.pendingSelectedType = null;
		}
	}
	
	private void save()
	{
		EditText nameField = (EditText)findViewById(R.id.name);
		String name = nameField.getText().toString();
		
		if (!name.isEmpty())
		{
			if (this.product == null)
			{
				create();
			}
			else
			{
				update();
			}
		}
		else
		{
			Toast.makeText(this, R.string.error_missing_name, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void create()
	{
		Spinner typeField = (Spinner)findViewById(R.id.type);
		Type type = (Type)typeField.getSelectedItem();
		
		EditText nameField = (EditText)findViewById(R.id.name);
		String name = nameField.getText().toString();
		
		ProductDao productDao = new ProductDao();
		Product product = productDao.createProduct(name, type);
		
		if (product != null)
		{
			Intent intent = new Intent();
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		else
		{
			Toast.makeText(this, R.string.error_creating_product, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void update()
	{
		Spinner typeField = (Spinner)findViewById(R.id.type);
		Type type = (Type)typeField.getSelectedItem();
		
		EditText nameField = (EditText)findViewById(R.id.name);
		String name = nameField.getText().toString();
		
		Product updatedProduct = new Product(this.product.getId(), name, type, this.product.getQuantity(), this.product.isSelected());
		
		ProductDao productDao = new ProductDao();
		
		if (productDao.updateProduct(updatedProduct))
		{
			Intent intent = new Intent();
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		else
		{
			Toast.makeText(this, R.string.error_editing_product, Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_edit_bar, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.save:
				save();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}