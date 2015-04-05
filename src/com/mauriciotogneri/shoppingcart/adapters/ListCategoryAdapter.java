package com.mauriciotogneri.shoppingcart.adapters;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.ListCategoryAdapter.ViewHolder;
import com.mauriciotogneri.shoppingcart.model.Category;

public class ListCategoryAdapter extends BaseListAdapter<Category, ViewHolder>
{
	public ListCategoryAdapter(Context context)
	{
		super(context, R.layout.list_category_row);
	}
	
	@Override
	protected ViewHolder getViewHolder(View view)
	{
		return new ViewHolder(view);
	}
	
	@Override
	protected void fillView(ViewHolder viewHolder, Category category, int position)
	{
		viewHolder.name.setText(category.getName());
		viewHolder.color.setBackgroundColor(category.getIntColor());
	}
	
	public void refresh(List<Category> list)
	{
		clear();
		addAll(list);
		notifyDataSetChanged();
	}
	
	protected static class ViewHolder
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