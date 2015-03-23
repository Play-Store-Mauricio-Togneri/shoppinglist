package com.mauriciotogneri.shoppingcart.views;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.SpinnerCategoryAdapter;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class UpdateProductView extends BaseView
{
	private SpinnerCategoryAdapter spinnerCategoryAdapter;
	
	public void initialize(Context context, Product product, Category initialCategory, final Observer observer)
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
				observer.onUpdateImage();
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
	
	public void refreshCategories()
	{
		this.spinnerCategoryAdapter.refresh();
	}
	
	public void setCategory(Category category)
	{
		Spinner productCategory = getSpinner(R.id.category);
		productCategory.setSelection(this.spinnerCategoryAdapter.getPositionOf(category));
	}
	
	@Override
	protected int getViewId()
	{
		return R.layout.activity_update_product;
	}
	
	public interface Observer
	{
		void onManageCategories();
		
		void onUpdateImage();
		
		void onUpdateProduct();
	}
}