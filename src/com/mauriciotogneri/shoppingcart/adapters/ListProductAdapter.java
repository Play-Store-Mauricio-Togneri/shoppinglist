package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.activeandroid.Model;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class ListProductAdapter extends ArrayAdapter<Product>
{
	private final LayoutInflater inflater;
	
	public ListProductAdapter(Context context)
	{
		super(context, android.R.layout.simple_list_item_1, new ArrayList<Product>());
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	@SuppressLint("InflateParams")
	public View getView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		Product product = getItem(position);
		
		if (convertView == null)
		{
			convertView = this.inflater.inflate(R.layout.list_product_row, parent, false);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(product.getName());
		
		ProductImage productImage = (ProductImage)convertView.findViewById(R.id.thumbnail);
		productImage.setImage(product.getImage());
		
		return convertView;
	}
	
	public void refresh(Category category)
	{
		if (category != null)
		{
			clear();
			
			List<Product> filtered = new ArrayList<Product>();
			List<Product> products = Model.all(Product.class);
			
			for (Product product : products)
			{
				if (product.isCategory(category) && (!product.isInCart()))
				{
					filtered.add(product);
				}
			}
			
			addAll(filtered);
			
			sort(new Comparator<Product>()
			{
				@Override
				public int compare(Product lhs, Product rhs)
				{
					return lhs.getName().compareTo(rhs.getName());
				}
			});
			
			notifyDataSetChanged();
		}
	}
}