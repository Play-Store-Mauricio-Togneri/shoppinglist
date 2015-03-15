package com.mauriciotogneri.shoppingcart.activities;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.ListCategoryAdapter;
import com.mauriciotogneri.shoppingcart.adapters.MenuItemAdapter;
import com.mauriciotogneri.shoppingcart.adapters.MenuItemAdapter.Option;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.CustomDialog;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;

public class ManageCategoriesActivity extends BaseActivity
{
	public static final String RESULT_CATEGORY = "category";
	
	private ListCategoryAdapter listCategoryAdapter;
	
	@Override
	protected void init()
	{
		setContentView(R.layout.activity_manage_categories);
		
		this.listCategoryAdapter = new ListCategoryAdapter(this);
		
		ListView listView = getListView(R.id.category_list);
		listView.setAdapter(this.listCategoryAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Category category = (Category)parent.getItemAtPosition(position);
				selectCategory(category);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				Category category = (Category)parent.getItemAtPosition(position);
				showCategoryOptions(category);
				
				return true;
			}
		});
		
		setButtonAction(R.id.create_category, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				updateCategory(null);
			}
		});
		
		refreshList();
	}
	
	private void selectCategory(final Category category)
	{
		Intent intent = new Intent();
		intent.putExtra(ManageCategoriesActivity.RESULT_CATEGORY, category);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	@SuppressLint("InflateParams")
	private void showCategoryOptions(final Category category)
	{
		final int EDIT_CATEGORY = 0;
		final int REMOVE_CATEGORY = 1;
		
		List<Option> optionsList = new ArrayList<Option>();
		optionsList.add(new Option(getString(R.string.icon_edit), getString(R.string.button_edit)));
		optionsList.add(new Option(getString(R.string.icon_remove), getString(R.string.button_remove)));
		
		CustomDialog dialog = new CustomDialog(this, category.getName());
		
		ListAdapter menuItemAdapter = new MenuItemAdapter(this, optionsList);
		dialog.setAdapter(menuItemAdapter, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int index)
			{
				switch (index)
				{
					case EDIT_CATEGORY:
						updateCategory(category);
						break;
					
					case REMOVE_CATEGORY:
						removeCategory(category);
						break;
				}
			}
		});
		
		dialog.display();
	}
	
	@SuppressLint("InflateParams")
	private void updateCategory(final Category category)
	{
		CustomDialog dialog = new CustomDialog(this, getString(R.string.label_product_category));
		dialog.setLayout(R.layout.dialog_create_category);
		
		final CustomEditText categoryName = dialog.getCustomEditText(R.id.name);
		
		if (category != null)
		{
			categoryName.setTextValue(category.getName());
		}
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String name = categoryName.getTextValue();
				
				if (!TextUtils.isEmpty(name))
				{
					if (!Category.exists(name))
					{
						if (category != null)
						{
							category.update(name);
						}
						else
						{
							Category newCategory = new Category(name);
							newCategory.save();
						}
						
						refreshList();
					}
					else
					{
						updateCategory(category);
						showToast(R.string.error_category_already_exists);
					}
				}
				else
				{
					updateCategory(category);
					showToast(R.string.error_invalid_name);
				}
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.display();
	}
	
	@SuppressLint("InflateParams")
	private void removeCategory(final Category category)
	{
		CustomDialog dialog = new CustomDialog(this, category.getName());
		dialog.setLayout(R.layout.dialog_content_text);
		
		TextView text = dialog.getCustomTextView(R.id.text);
		text.setText(R.string.confirmation_remove_category);
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (!Product.exists(category))
				{
					category.delete();
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
		text.setText(R.string.error_category_in_use);
		
		dialog.setPositiveButton(R.string.button_accept, null);
		
		dialog.display();
	}
	
	private void refreshList()
	{
		this.listCategoryAdapter.refresh();
		
		ListView listView = getListView(R.id.category_list);
		TextView emptyLabel = getCustomTextView(R.id.empty_label);
		
		if (this.listCategoryAdapter.getCount() > 0)
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