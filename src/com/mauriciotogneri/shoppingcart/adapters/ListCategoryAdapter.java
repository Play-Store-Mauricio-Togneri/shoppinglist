package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Category;

public class ListCategoryAdapter extends ArrayAdapter<Category>
{
	public ListCategoryAdapter(Context context)
	{
		super(context, R.layout.list_category_row, R.id.name, new ArrayList<Category>());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = super.getView(position, convertView, parent);
		
		ViewHolder viewHolder = (ViewHolder)rowView.getTag();
		
		if (viewHolder == null)
		{
			viewHolder = new ViewHolder(rowView);
			rowView.setTag(viewHolder);
		}
		
		Category category = getItem(position);
		
		viewHolder.name.setText(category.getName());
		viewHolder.color.setBackgroundColor(category.getIntColor());
		
		return rowView;
	}
	
	public void refresh(List<Category> list)
	{
		clear();
		addAll(list);
		notifyDataSetChanged();
	}
	
	private static class ViewHolder
	{
		public TextView name;
		public TextView color;
		
		public ViewHolder(View view)
		{
			this.name = (TextView)view.findViewById(R.id.name);
			this.color = (TextView)view.findViewById(R.id.color);
		}
	}
}