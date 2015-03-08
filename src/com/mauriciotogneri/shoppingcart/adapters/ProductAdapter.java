package com.mauriciotogneri.shoppingcart.adapters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.activeandroid.Model;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;

public class ProductAdapter extends ArrayAdapter<Product>
{
	private final LayoutInflater inflater;
	
	public ProductAdapter(Context context)
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
			convertView = this.inflater.inflate(R.layout.activity_add_product_row, null);
		}
		
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(product.getName());
		
		ImageView thumbnail = (ImageView)convertView.findViewById(R.id.thumbnail);
		byte[] picture = product.getPicture();
		
		if ((picture != null) && (picture.length > 0))
		{
			Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
			thumbnail.setImageBitmap(bitmap);
		}
		
		return convertView;
	}
	
	public void update(Category category)
	{
		clear();
		
		List<Product> products = Model.all(Product.class);
		
		if (category != null)
		{
			List<Product> filtered = new ArrayList<Product>();
			
			for (Product product : products)
			{
				if (product.isCategory(category) && (!product.isInCart()))
				{
					filtered.add(product);
				}
			}
			
			addAll(filtered);
		}
		else
		{
			addAll(products);
		}
		
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