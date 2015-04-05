package com.mauriciotogneri.shoppingcart.adapters;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.MenuItemAdapter.Option;

public class MenuItemAdapter extends ArrayAdapter<Option>
{
	public MenuItemAdapter(Context context, List<Option> options)
	{
		super(context, R.layout.menu_item_row, R.id.title, options);
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
		
		Option option = getItem(position);
		
		viewHolder.icon.setText(option.icon);
		viewHolder.title.setText(option.title);
		
		return rowView;
	}
	
	public static class Option
	{
		public final String icon;
		public final String title;
		
		public Option(String icon, String title)
		{
			this.icon = icon;
			this.title = title;
		}
	}
	
	private static class ViewHolder
	{
		public TextView icon;
		public TextView title;
		
		public ViewHolder(View view)
		{
			this.icon = (TextView)view.findViewById(R.id.icon);
			this.title = (TextView)view.findViewById(R.id.title);
		}
	}
}