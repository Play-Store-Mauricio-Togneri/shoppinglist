package com.mauriciotogneri.shoppingcart.views;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.MenuItemAdapter;
import com.mauriciotogneri.shoppingcart.adapters.MenuItemAdapter.Option;
import com.mauriciotogneri.shoppingcart.adapters.SpinnerCategoryAdapter;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomDialog;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class UpdateProductView extends BaseView
{
	private SpinnerCategoryAdapter spinnerCategoryAdapter;
	
	public void initialize(final Context context, Product product, Category initialCategory, List<Category> list, final Observer observer)
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
		
		this.spinnerCategoryAdapter = new SpinnerCategoryAdapter(context);
		this.spinnerCategoryAdapter.refresh(list);
		
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
		
		productCategory.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				Category category = (Category)parent.getItemAtPosition(position);
				observer.onCategorySelected(category);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		
		// ---------------------------
		
		setButtonAction(R.id.button_manage_categories, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				observer.onManageCategories();
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
				chooseImageSource(context, observer);
			}
		});
		
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
				observer.onUpdateProduct();
			}
		});
	}
	
	private void chooseImageSource(Context context, final Observer observer)
	{
		final int SOURCE_GALERY = 0;
		final int SOURCE_GAMERA = 1;
		
		List<Option> optionsList = new ArrayList<Option>();
		optionsList.add(new Option(context.getString(R.string.icon_galery), context.getString(R.string.label_source_galery)));
		optionsList.add(new Option(context.getString(R.string.icon_camera), context.getString(R.string.label_source_camera)));
		
		CustomDialog dialog = new CustomDialog(context, context.getString(R.string.label_select_picture_source));
		
		ListAdapter menuItemAdapter = new MenuItemAdapter(context, optionsList);
		dialog.setAdapter(menuItemAdapter, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int index)
			{
				switch (index)
				{
					case SOURCE_GALERY:
						observer.onUpdateImageGalery();
						break;
					
					case SOURCE_GAMERA:
						observer.onUpdateImageCamera();
						break;
				}
			}
		});
		
		dialog.display();
	}
	
	public void setProductImage(byte[] image)
	{
		ProductImage productImage = getProductImage(R.id.thumbnail);
		productImage.setImage(image);
	}
	
	public void setNameInputError(Context context, int textId)
	{
		CustomEditText productName = getCustomEditText(R.id.name);
		productName.setErrorText(context.getString(textId));
	}
	
	public void removeInputNameError()
	{
		CustomEditText productName = getCustomEditText(R.id.name);
		productName.removeErrorText();
	}
	
	public Category getProductCategory()
	{
		Spinner productCategory = getSpinner(R.id.category);
		
		return (Category)productCategory.getSelectedItem();
	}
	
	public String getProductName()
	{
		CustomEditText productName = getCustomEditText(R.id.name);
		
		return productName.getTextValue();
	}
	
	public void refreshCategories(List<Category> list)
	{
		this.spinnerCategoryAdapter.refresh(list);
	}
	
	public void setCategory(Category category)
	{
		Spinner productCategory = getSpinner(R.id.category);
		productCategory.setSelection(this.spinnerCategoryAdapter.getPositionOf(category));
	}
	
	@Override
	protected int getViewId()
	{
		return R.layout.fragment_update_product;
	}
	
	public interface Observer
	{
		void onManageCategories();
		
		void onUpdateImageGalery();
		
		void onUpdateImageCamera();
		
		void onUpdateProduct();
		
		void onCategorySelected(Category category);
	}
}