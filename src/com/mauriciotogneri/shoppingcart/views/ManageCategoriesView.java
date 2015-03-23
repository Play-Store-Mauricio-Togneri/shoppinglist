package com.mauriciotogneri.shoppingcart.views;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.mauriciotogneri.shoppingcart.widgets.CustomDialog;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;
import com.mauriciotogneri.shoppingcart.widgets.CustomTextView;

public class ManageCategoriesView extends BaseView
{
	private ListCategoryAdapter listCategoryAdapter;
	
	private String selectedColor = "";
	
	public void initialize(final Context context, final Observer observer)
	{
		this.listCategoryAdapter = new ListCategoryAdapter(context);
		
		ListView listView = getListView(R.id.category_list);
		listView.setAdapter(this.listCategoryAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Category category = (Category)parent.getItemAtPosition(position);
				observer.onCategorySelected(category);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				Category category = (Category)parent.getItemAtPosition(position);
				displayCategoryOptions(context, category, observer);
				
				return true;
			}
		});
		
		setButtonAction(R.id.create_category, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				editCategory(context, null, observer);
			}
		});
		
		refreshList();
	}
	
	@SuppressLint("InflateParams")
	private void displayCategoryOptions(final Context context, final Category category, final Observer observer)
	{
		final int EDIT_CATEGORY = 0;
		final int REMOVE_CATEGORY = 1;
		
		List<Option> optionsList = new ArrayList<Option>();
		optionsList.add(new Option(context.getString(R.string.icon_edit), context.getString(R.string.button_edit)));
		optionsList.add(new Option(context.getString(R.string.icon_remove), context.getString(R.string.button_remove)));
		
		CustomDialog dialog = new CustomDialog(context, category.getName());
		
		ListAdapter menuItemAdapter = new MenuItemAdapter(context, optionsList);
		dialog.setAdapter(menuItemAdapter, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int index)
			{
				switch (index)
				{
					case EDIT_CATEGORY:
						editCategory(context, category, observer);
						break;
					
					case REMOVE_CATEGORY:
						removeCategory(context, category, observer);
						break;
				}
			}
		});
		
		dialog.display();
	}
	
	public void editCategory(Context context, final Category category, final Observer observer)
	{
		CustomDialog dialog = new CustomDialog(context, context.getString(R.string.label_product_category));
		dialog.setLayout(R.layout.dialog_create_category);
		
		final CustomEditText categoryName = dialog.getCustomEditText(R.id.name);
		
		if (category != null)
		{
			categoryName.setTextValue(category.getName());
		}
		
		this.selectedColor = (category == null) ? Category.COLOR_1 : category.getColor();
		
		setColorButtonCallback(dialog, R.id.color_1, Category.COLOR_1, this.selectedColor);
		setColorButtonCallback(dialog, R.id.color_2, Category.COLOR_2, this.selectedColor);
		setColorButtonCallback(dialog, R.id.color_3, Category.COLOR_3, this.selectedColor);
		setColorButtonCallback(dialog, R.id.color_4, Category.COLOR_4, this.selectedColor);
		setColorButtonCallback(dialog, R.id.color_5, Category.COLOR_5, this.selectedColor);
		setColorButtonCallback(dialog, R.id.color_6, Category.COLOR_6, this.selectedColor);
		setColorButtonCallback(dialog, R.id.color_7, Category.COLOR_7, this.selectedColor);
		setColorButtonCallback(dialog, R.id.color_8, Category.COLOR_8, this.selectedColor);
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				observer.onEditCategory(category, categoryName.getTextValue(), ManageCategoriesView.this.selectedColor);
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.display();
	}
	
	@SuppressLint("InflateParams")
	private void removeCategory(final Context context, final Category category, final Observer observer)
	{
		CustomDialog dialog = new CustomDialog(context, category.getName());
		dialog.setLayout(R.layout.dialog_content_text);
		
		TextView text = dialog.getCustomTextView(R.id.text);
		text.setText(R.string.confirmation_remove_category);
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				observer.onRemoveCategory(category);
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
		text.setText(R.string.error_category_in_use);
		
		dialog.setPositiveButton(R.string.button_accept, null);
		
		dialog.display();
	}
	
	private void setColorButtonCallback(final CustomDialog dialog, final int textViewId, String colorCode, String selectedColor)
	{
		CustomTextView customTextView = dialog.getCustomTextView(textViewId);
		customTextView.setBackgroundColor(Color.parseColor("#" + colorCode));
		
		if (selectedColor.equals(colorCode))
		{
			selectColor(dialog, textViewId);
		}
		
		customTextView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				selectColor(dialog, textViewId);
			}
		});
	}
	
	private void selectColor(CustomDialog dialog, int colorId)
	{
		CustomTextView color1 = dialog.getCustomTextView(R.id.color_1);
		color1.setText("");
		
		CustomTextView color2 = dialog.getCustomTextView(R.id.color_2);
		color2.setText("");
		
		CustomTextView color3 = dialog.getCustomTextView(R.id.color_3);
		color3.setText("");
		
		CustomTextView color4 = dialog.getCustomTextView(R.id.color_4);
		color4.setText("");
		
		CustomTextView color5 = dialog.getCustomTextView(R.id.color_5);
		color5.setText("");
		
		CustomTextView color6 = dialog.getCustomTextView(R.id.color_6);
		color6.setText("");
		
		CustomTextView color7 = dialog.getCustomTextView(R.id.color_7);
		color7.setText("");
		
		CustomTextView color8 = dialog.getCustomTextView(R.id.color_8);
		color8.setText("");
		
		CustomTextView color = dialog.getCustomTextView(colorId);
		color.setText(R.string.icon_update);
		
		ColorDrawable colorDrawable = (ColorDrawable)color.getBackground();
		this.selectedColor = String.format("%06X", 0xFFFFFF & colorDrawable.getColor());
	}
	
	public void refreshList()
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
	
	@Override
	protected int getViewId()
	{
		return R.layout.fragment_manage_categories;
	}
	
	public interface Observer
	{
		void onCategorySelected(Category category);
		
		void onEditCategory(Category category, String name, String color);
		
		void onRemoveCategory(Category category);
	}
}