package com.mauriciotogneri.shoppingcart.activities;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.SpinnerCategoryAdapter;
import com.mauriciotogneri.shoppingcart.adapters.MenuProductAdapter;
import com.mauriciotogneri.shoppingcart.adapters.MenuProductAdapter.Option;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomDialog;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class ManageCategoriesActivity extends Activity
{
	private SpinnerCategoryAdapter productAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// this.productAdapter = new CategoryAdapter(this);
		
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
		
		TextView createCategory = (TextView)findViewById(R.id.create_category);
		createCategory.setOnClickListener(new View.OnClickListener()
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
		CustomDialog dialog = new CustomDialog(this, product.getName());
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.dialog_cart_item, null);
		dialog.setView(layout);
		
		ProductImage productImage = (ProductImage)layout.findViewById(R.id.thumbnail);
		productImage.setImage(product.getImage());
		
		final NumberPicker quantity = (NumberPicker)layout.findViewById(R.id.quantity);
		quantity.setMinValue(1);
		quantity.setMaxValue(100);
		quantity.setValue(1);
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				addProduct(product, quantity.getValue());
				dialog.dismiss();
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.display();
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
		
		CustomDialog dialog = new CustomDialog(this, product.getName());
		
		ListAdapter adapter = new MenuProductAdapter(this, optionsList);
		dialog.setAdapter(adapter, new OnClickListener()
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
		
		dialog.display();
	}
	
	private void createProduct()
	{
		Intent intent = new Intent(this, UpdateProductActivity.class);
		startActivity(intent);
	}
	
	private void editProduct(Product product)
	{
		Intent intent = new Intent(this, UpdateProductActivity.class);
		intent.putExtra(UpdateProductActivity.PARAMETER_PRODUCT_ID, product.getId());
		startActivity(intent);
	}
	
	@SuppressLint("InflateParams")
	private void removeProduct(final Product product)
	{
		CustomDialog dialog = new CustomDialog(this, product.getName());
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.dialog_confirm, null);
		dialog.setView(layout);
		
		TextView text = (TextView)layout.findViewById(R.id.text);
		text.setText(R.string.confirmation_remove_product);
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				product.delete();
				refreshList();
				
				dialog.dismiss();
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.display();
	}
	
	private void refreshList()
	{
		Spinner categoryField = (Spinner)findViewById(R.id.category);
		Category category = (Category)categoryField.getSelectedItem();
		// this.productAdapter.refresh(category);
		
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