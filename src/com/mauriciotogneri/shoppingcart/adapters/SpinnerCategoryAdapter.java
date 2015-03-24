package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.dao.CategoryDao;
import com.mauriciotogneri.shoppingcart.model.Category;

public class SpinnerCategoryAdapter extends ArrayAdapter<Category>
{
	private final LayoutInflater inflater;
	
	public SpinnerCategoryAdapter(Context context)
	{
		super(context, R.layout.spinner_category_header, new ArrayList<Category>());
		
		this.inflater = LayoutInflater.from(context);
		
		setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	
	@Override
	@SuppressLint("InflateParams")
	public View getView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		Category category = getItem(position);
		
		if (convertView == null)
		{
			convertView = this.inflater.inflate(R.layout.spinner_category_header, parent, false);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.title);
		name.setText(category.getName());
		
		return convertView;
	}
	
	@Override
	@SuppressLint("InflateParams")
	public View getDropDownView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		Category category = getItem(position);
		
		if (convertView == null)
		{
			convertView = this.inflater.inflate(R.layout.spinner_category_dropdown, parent, false);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.title);
		name.setText(category.getName());
		
		return convertView;
	}
	
	public int getPositionOf(Category category)
	{
		int result = -1;
		int limit = getCount();
		
		for (int i = 0; i < limit; i++)
		{
			Category current = getItem(i);
			
			if (current.getName().equals(category.getName()))
			{
				result = i;
				break;
			}
		}
		
		return result;
	}
	
	public void refresh()
	{
		CategoryDao categoryDao = new CategoryDao();
		List<Category> list = categoryDao.getCategories();
		
		clear();
		addAll(list);
		notifyDataSetChanged();
	}
}