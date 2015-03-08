package com.mauriciotogneri.shoppingcart.activities;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import com.activeandroid.Model;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.CategoryAdapter;
import com.mauriciotogneri.shoppingcart.adapters.ProductAdapter;
import com.mauriciotogneri.shoppingcart.adapters.ProductOptionAdapter;
import com.mauriciotogneri.shoppingcart.adapters.ProductOptionAdapter.Option;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;

public class AddProductActivity extends Activity
{
	private ProductAdapter productAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		this.productAdapter = new ProductAdapter(this);
		
		List<Category> categories = Model.all(Category.class);
		
		CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
		
		Spinner category = (Spinner)findViewById(R.id.category);
		category.setAdapter(categoryAdapter);
		category.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				refreshList();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		
		ListView listView = (ListView)findViewById(R.id.product_list);
		listView.setAdapter(this.productAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Product product = (Product)parent.getItemAtPosition(position);
				selectProduct(product);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				Product product = (Product)parent.getItemAtPosition(position);
				showProductOptions(product);
				
				return true;
			}
		});
		
		TextView createProduct = (TextView)findViewById(R.id.create_product);
		createProduct.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				createProduct();
			}
		});
	}
	
	@SuppressLint("InflateParams")
	private void selectProduct(final Product product)
	{
		LinearLayout customTitle = (LinearLayout)getLayoutInflater().inflate(R.layout.dialog_title, null);
		TextView dialogTitle = (TextView)customTitle.findViewById(R.id.title);
		dialogTitle.setText(product.getName());
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCustomTitle(customTitle);
		builder.setCancelable(true);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.dialog_cart_item, null);
		builder.setView(layout);
		
		ImageView thumbnail = (ImageView)layout.findViewById(R.id.thumbnail);
		byte[] picture = product.getPicture();
		
		if ((picture != null) && (picture.length > 0))
		{
			Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
			thumbnail.setImageBitmap(bitmap);
		}
		
		final NumberPicker quantity = (NumberPicker)layout.findViewById(R.id.quantity);
		quantity.setMinValue(1);
		quantity.setMaxValue(100);
		quantity.setValue(1);
		
		builder.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				addProduct(product, quantity.getValue());
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton(R.string.button_cancel, null);
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void addProduct(Product product, int quantity)
	{
		CartItem cartItem = new CartItem(product, quantity, false);
		cartItem.save();
		
		refreshList();
	}
	
	@SuppressLint("InflateParams")
	private void showProductOptions(final Product product)
	{
		final int EDIT_PRODUCT = 0;
		final int REMOVE_PRODCUT = 1;
		
		List<Option> optionsList = new ArrayList<Option>();
		optionsList.add(new Option(getString(R.string.icon_edit), getString(R.string.button_edit)));
		optionsList.add(new Option(getString(R.string.icon_remove), getString(R.string.button_remove)));
		
		ListAdapter adapter = new ProductOptionAdapter(this, optionsList);
		
		LinearLayout customTitle = (LinearLayout)getLayoutInflater().inflate(R.layout.dialog_title, null);
		TextView dialogTitle = (TextView)customTitle.findViewById(R.id.title);
		dialogTitle.setText(product.getName());
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCustomTitle(customTitle);
		builder.setCancelable(true);
		builder.setAdapter(adapter, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int index)
			{
				switch (index)
				{
					case EDIT_PRODUCT:
						editProduct(product);
						break;
					
					case REMOVE_PRODCUT:
						removeProduct(product);
						break;
				}
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void createProduct()
	{
		// Intent intent = new Intent(this, EditProductActivity.class);
		// startActivity(intent);
	}
	
	private void editProduct(Product product)
	{
		// Intent intent = new Intent(this, EditProductActivity.class);
		// intent.putExtra(EditProductActivity.PARAMETER_PRODUCT, product);
		// startActivity(intent);
	}
	
	private void removeProduct(Product product)
	{
		// ProductDao productDao = new ProductDao();
		//
		// if (productDao.removeProduct(product))
		// {
		// this.filteredList.remove(product);
		// this.totalList.remove(product);
		// refreshList();
		// }
		// else
		// {
		// Toast.makeText(this, R.string.error_removing_product, Toast.LENGTH_SHORT).show();
		// }
	}
	
	private void refreshList()
	{
		Spinner categoryField = (Spinner)findViewById(R.id.category);
		Category category = (Category)categoryField.getSelectedItem();
		this.productAdapter.update(category);
		
		ListView listView = (ListView)findViewById(R.id.product_list);
		TextView emptyLabel = (TextView)findViewById(R.id.empty_label);
		
		if (this.productAdapter.getCount() > 0)
		{
			listView.setVisibility(View.VISIBLE);
			emptyLabel.setVisibility(View.GONE);
		}
		else
		{
			listView.setVisibility(View.GONE);
			emptyLabel.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		refreshList();
	}
}