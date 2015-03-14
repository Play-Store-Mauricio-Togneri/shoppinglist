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
import com.activeandroid.Model;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class ListCategoryAdapter extends ArrayAdapter<Category>
{
	private final LayoutInflater inflater;
	
	public ListCategoryAdapter(Context context)
	{
		super(context, android.R.layout.simple_list_item_1, new ArrayList<Category>());
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	@SuppressLint("InflateParams")
	public View getView(int position, View originalView, ViewGroup parent)
	{
		View convertView = originalView;
		Category category = getItem(position);
		
		if (convertView == null)
		{
			convertView = this.inflater.inflate(R.layout.list_product_row, null);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(category.getName());
		
		ProductImage productImage = (ProductImage)convertView.findViewById(R.id.thumbnail);
		// productImage.setImage(category.getImage());
		
		return convertView;
	}
	
	public void refresh(Category category)
	{
		clear();
		
		List<Product> products = Model.all(Product.class);
		
		// if (category != null)
		// {
		// List<Product> filtered = new ArrayList<Product>();
		//
		// for (Product product : products)
		// {
		// if (product.isCategory(category) && (!product.isInCart()))
		// {
		// filtered.add(product);
		// }
		// }
		//
		// addAll(filtered);
		// }
		// else
		// {
		// addAll(products);
		// }
		//
		// sort(new Comparator<Product>()
		// {
		// @Override
		// public int compare(Product lhs, Product rhs)
		// {
		// return lhs.getName().compareTo(rhs.getName());
		// }
		// });
		
		notifyDataSetChanged();
	}
}