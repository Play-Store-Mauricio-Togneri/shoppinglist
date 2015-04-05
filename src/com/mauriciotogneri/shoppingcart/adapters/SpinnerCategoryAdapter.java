package com.mauriciotogneri.shoppingcart.adapters;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.SpinnerCategoryAdapter.ViewHolder;
import com.mauriciotogneri.shoppingcart.model.Category;

public class SpinnerCategoryAdapter extends BaseListAdapter<Category, ViewHolder>
{
	public SpinnerCategoryAdapter(Context context)
	{
		super(context, R.layout.spinner_category_header);
		
		setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	
	@Override
	protected ViewHolder getViewHolder(View view)
	{
		return new ViewHolder(view);
	}
	
	@Override
	protected void fillView(ViewHolder viewHolder, Category category)
	{
		viewHolder.title.setText(category.getName());
	}
	
	protected static class ViewHolder
	{
		public TextView title;
		
		public ViewHolder(View view)
		{
			this.title = (TextView)view.findViewById(R.id.title);
		}
	}
	
	@Override
	@SuppressLint("InflateParams")
	public View getDropDownView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		Category category = getItem(position);
		
		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.spinner_category_dropdown, parent, false);
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
	
	public void refresh(List<Category> list)
	{
		clear();
		addAll(list);
		notifyDataSetChanged();
	}
}