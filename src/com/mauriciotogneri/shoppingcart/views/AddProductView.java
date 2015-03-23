package com.mauriciotogneri.shoppingcart.views;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomDialog;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class AddProductView extends BaseView
{
	private ListProductAdapter listProductAdapter;
	private SpinnerCategoryAdapter spinnerCategoryAdapter;
	
	public void initialize(final Context context, final Observer observer)
	{
		this.listProductAdapter = new ListProductAdapter(context);
		this.spinnerCategoryAdapter = new SpinnerCategoryAdapter(context);
		
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
				selectProduct(context, product, observer);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				Product product = (Product)parent.getItemAtPosition(position);
				displayProductOptions(context, product, observer);
				
				return true;
			}
		});
		
		setButtonAction(R.id.create_product, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				observer.onCreateProduct();
			}
		});
	}
	
	@SuppressLint("InflateParams")
	private void selectProduct(Context context, final Product product, final Observer observer)
	{
		CustomDialog dialog = new CustomDialog(context, product.getName());
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
				observer.onAddProduct(product, quantity.getValue());
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.display();
	}
	
	@SuppressLint("InflateParams")
	private void displayProductOptions(final Context context, final Product product, final Observer observer)
	{
		final int EDIT_PRODUCT = 0;
		final int REMOVE_PRODCUT = 1;
		
		List<Option> optionsList = new ArrayList<Option>();
		optionsList.add(new Option(context.getString(R.string.icon_edit), context.getString(R.string.button_edit)));
		optionsList.add(new Option(context.getString(R.string.icon_remove), context.getString(R.string.button_remove)));
		
		CustomDialog dialog = new CustomDialog(context, product.getName());
		
		ListAdapter menuItemAdapter = new MenuItemAdapter(context, optionsList);
		dialog.setAdapter(menuItemAdapter, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int index)
			{
				switch (index)
				{
					case EDIT_PRODUCT:
						observer.onEditProduct(product);
						break;
					
					case REMOVE_PRODCUT:
						removeProduct(context, product, observer);
						break;
				}
			}
		});
		
		dialog.display();
	}
	
	@SuppressLint("InflateParams")
	private void removeProduct(Context context, final Product product, final Observer observer)
	{
		CustomDialog dialog = new CustomDialog(context, product.getName());
		dialog.setLayout(R.layout.dialog_content_text);
		
		TextView text = dialog.getCustomTextView(R.id.text);
		text.setText(R.string.confirmation_remove_product);
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				observer.onRemoveProduct(product);
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.display();
	}
	
	@SuppressLint("InflateParams")
	public void showError(Context context)
	{
		CustomDialog dialog = new CustomDialog(context, context.getString(R.string.label_error));
		dialog.setLayout(R.layout.dialog_content_text);
		
		TextView text = dialog.getCustomTextView(R.id.text);
		text.setText(R.string.error_product_in_use);
		
		dialog.setPositiveButton(R.string.button_accept, null);
		
		dialog.display();
	}
	
	public void refreshList()
	{
		Spinner categoryField = getSpinner(R.id.category);
		Category category = (Category)categoryField.getSelectedItem();
		this.listProductAdapter.refresh(category);
		
		if (category != null)
		{
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
	}
	
	public void refreshCategories()
	{
		this.spinnerCategoryAdapter.refresh();
	}
	
	public Category getSelectedCategory()
	{
		Spinner categorySpinner = getSpinner(R.id.category);
		
		return (Category)categorySpinner.getSelectedItem();
	}
	
	public void setCategory(Category category)
	{
		Spinner productCategory = getSpinner(R.id.category);
		productCategory.setSelection(this.spinnerCategoryAdapter.getPositionOf(category));
	}
	
	@Override
	protected int getViewId()
	{
		return R.layout.fragment_add_product;
	}
	
	public interface Observer
	{
		void onAddProduct(Product product, int value);
		
		void onCreateProduct();
		
		void onEditProduct(Product product);
		
		void onRemoveProduct(Product product);
	}
}