package com.mauriciotogneri.shoppingcart.activities;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.ListProductAdapter;
import com.mauriciotogneri.shoppingcart.adapters.MenuItemAdapter;
import com.mauriciotogneri.shoppingcart.adapters.MenuItemAdapter.Option;
import com.mauriciotogneri.shoppingcart.adapters.SpinnerCategoryAdapter;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomDialog;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class AddProductActivity extends BaseActivity
{
	private ListProductAdapter listProductAdapter;
	private SpinnerCategoryAdapter spinnerCategoryAdapter;
	
	@Override
	protected void init()
	{
		setContentView(R.layout.activity_add_product);
		
		this.listProductAdapter = new ListProductAdapter(this);
		
		this.spinnerCategoryAdapter = new SpinnerCategoryAdapter(this);
		
		Spinner categorySpinner = getSpinner(R.id.category);
		categorySpinner.setAdapter(this.spinnerCategoryAdapter);
		categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener()
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
		
		ListView listView = getListView(R.id.product_list);
		listView.setAdapter(this.listProductAdapter);
		
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
		
		setButtonAction(R.id.create_product, new View.OnClickListener()
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
		dialog.setLayout(R.layout.dialog_cart_item);
		
		ProductImage productImage = dialog.getProductImage(R.id.thumbnail);
		productImage.setImage(product.getImage());
		
		final NumberPicker quantity = dialog.getNumberPicker(R.id.quantity);
		quantity.setMinValue(1);
		quantity.setMaxValue(100);
		quantity.setValue(1);
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				addProduct(product, quantity.getValue());
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
		
		ListAdapter menuItemAdapter = new MenuItemAdapter(this, optionsList);
		dialog.setAdapter(menuItemAdapter, new OnClickListener()
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
		Spinner categorySpinner = getSpinner(R.id.category);
		
		Intent intent = new Intent(this, UpdateProductActivity.class);
		intent.putExtra(UpdateProductActivity.PARAMETER_CATEGORY, (Category)categorySpinner.getSelectedItem());
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
		dialog.setLayout(R.layout.dialog_content_text);
		
		TextView text = dialog.getCustomTextView(R.id.text);
		text.setText(R.string.confirmation_remove_product);
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (!CartItem.exists(product))
				{
					product.delete();
					refreshList();
				}
				else
				{
					showError();
				}
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.display();
	}
	
	@SuppressLint("InflateParams")
	private void showError()
	{
		CustomDialog dialog = new CustomDialog(this, getString(R.string.label_error));
		dialog.setLayout(R.layout.dialog_content_text);
		
		TextView text = dialog.getCustomTextView(R.id.text);
		text.setText(R.string.error_product_in_use);
		
		dialog.setPositiveButton(R.string.button_accept, null);
		
		dialog.display();
	}
	
	private void refreshList()
	{
		Spinner categoryField = getSpinner(R.id.category);
		Category category = (Category)categoryField.getSelectedItem();
		this.listProductAdapter.refresh(category);
		
		ListView listView = getListView(R.id.product_list);
		TextView emptyLabel = getCustomTextView(R.id.empty_label);
		
		if (this.listProductAdapter.getCount() > 0)
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
		
		this.spinnerCategoryAdapter.refresh();
		refreshList();
	}
}