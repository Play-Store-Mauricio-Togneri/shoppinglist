package com.mauriciotogneri.shoppingcart.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.ListAdapter;
import com.mauriciotogneri.shoppingcart.database.ProductDao;
import com.mauriciotogneri.shoppingcart.objects.Product;
import com.mauriciotogneri.shoppingcart.screens.add.AddProductActivity;

public class MainActivity extends Activity
{
	private ListAdapter adapter;
	private final List<Product> cartList = new ArrayList<Product>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setTitle("   " + getString(R.string.app_name));
		
		this.adapter = new ListAdapter(this, this.cartList);
		
		ListView listView = (ListView)findViewById(R.id.list);
		listView.setAdapter(this.adapter);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				Product product = (Product)parent.getItemAtPosition(position);
				
				if (!product.isSelected())
				{
					displayProduct(product);
				}
				
				return true;
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Product product = (Product)parent.getItemAtPosition(position);
				selectProduct(product);
			}
		});
		
		updateList(true);
		
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// intent.setType("image/*");
		// startActivityForResult(intent, 123);
		
		Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/opensans.ttf");
		TextView sampleText = (TextView)findViewById(R.id.toolbar_title);
		sampleText.setTypeface(fontFamily);
		
		Typeface fontFamily1 = Typeface.createFromAsset(getAssets(), "fonts/glyphicons.ttf");
		TextView sampleText1 = (TextView)findViewById(R.id.button_glyphicons);
		sampleText1.setTypeface(fontFamily1);
		sampleText1.setText("\uE086");
		
		Typeface fontFamily2 = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
		TextView sampleText2 = (TextView)findViewById(R.id.button_fontawesome);
		sampleText2.setTypeface(fontFamily2);
		sampleText2.setText("\uF001");
		
		Typeface fontFamily3 = Typeface.createFromAsset(getAssets(), "fonts/icomoon.ttf");
		TextView sampleText3 = (TextView)findViewById(R.id.button_iconmoon);
		sampleText3.setTypeface(fontFamily3);
		sampleText3.setText("\uE962");
	}
	
	private void selectProduct(Product product)
	{
		product.setSelected(!product.isSelected());
		
		ProductDao productDao = new ProductDao();
		
		if (productDao.updateProduct(product))
		{
			updateList(true);
		}
	}
	
	private void updateList(boolean sort)
	{
		if (sort)
		{
			List<Product> notSelected = new ArrayList<Product>();
			List<Product> selected = new ArrayList<Product>();
			
			for (Product product : this.cartList)
			{
				if (product.isSelected())
				{
					selected.add(product);
				}
				else
				{
					notSelected.add(product);
				}
			}
			
			Collections.sort(notSelected, new Comparator<Product>()
			{
				@Override
				public int compare(Product lhs, Product rhs)
				{
					return lhs.getName().compareTo(rhs.getName());
				}
			});
			
			Collections.sort(selected, new Comparator<Product>()
			{
				@Override
				public int compare(Product lhs, Product rhs)
				{
					return lhs.getName().compareTo(rhs.getName());
				}
			});
			
			this.cartList.clear();
			this.cartList.addAll(notSelected);
			this.cartList.addAll(selected);
		}
		
		this.adapter.notifyDataSetChanged();
		
		if (this.cartList.size() > 0)
		{
			ListView listView = (ListView)findViewById(R.id.list);
			listView.setVisibility(View.VISIBLE);
			
			TextView emptyLabel = (TextView)findViewById(R.id.empty_label);
			emptyLabel.setVisibility(View.GONE);
		}
		else
		{
			ListView listView = (ListView)findViewById(R.id.list);
			listView.setVisibility(View.GONE);
			
			TextView emptyLabel = (TextView)findViewById(R.id.empty_label);
			emptyLabel.setVisibility(View.VISIBLE);
		}
	}
	
	@SuppressLint("InflateParams")
	private void displayProduct(final Product product)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(product.getName());
		builder.setCancelable(true);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.dialog_product, null);
		builder.setView(layout);
		
		ImageView image = (ImageView)layout.findViewById(R.id.image);
		image.setImageResource(product.getThumbnail());
		
		final NumberPicker quantity = (NumberPicker)layout.findViewById(R.id.quantity);
		quantity.setMinValue(1);
		quantity.setMaxValue(100);
		quantity.setValue(product.getQuantity());
		
		builder.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				updateQuantity(product, quantity.getValue());
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton(R.string.button_cancel, null);
		
		builder.setNeutralButton(R.string.button_remove, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				removeProduct(product);
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void updateQuantity(Product product, int quantity)
	{
		product.setQuantity(quantity);
		
		ProductDao productDao = new ProductDao();
		
		if (productDao.updateProduct(product))
		{
			updateList(false);
		}
	}
	
	private void removeProduct(Product product)
	{
		product.setQuantity(0);
		product.setSelected(false);
		this.cartList.remove(product);
		
		ProductDao productDao = new ProductDao();
		
		if (productDao.updateProduct(product))
		{
			updateList(false);
		}
	}
	
	private void addProduct()
	{
		Intent intent = new Intent(this, AddProductActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		// ProductDao productDao = new ProductDao();
		// this.productsList.clear();
		// this.productsList.addAll(productDao.getProducts(true));
		// updateList(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_list_bar, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.add_product:
				addProduct();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		// ProductDao productDao = new ProductDao();
		//
		// for (Product product : this.productsList)
		// {
		// if (product.isSelected())
		// {
		// product.setQuantity(0);
		// product.setSelected(false);
		// productDao.updateProduct(product);
		// }
		// }
	}
}