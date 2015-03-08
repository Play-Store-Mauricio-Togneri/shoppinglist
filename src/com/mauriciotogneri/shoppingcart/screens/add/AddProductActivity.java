package com.mauriciotogneri.shoppingcart.screens.add;

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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.database.ProductDao;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.screens.edit.EditProductActivity;

public class AddProductActivity extends Activity
{
	private AddProductAdapter adapter;
	private final List<Product> totalList = new ArrayList<Product>();
	private final List<Product> filteredList = new ArrayList<Product>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		this.adapter = new AddProductAdapter(this, this.filteredList);
		
		Spinner category = (Spinner)findViewById(R.id.category);
		ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, R.layout.spinner_type_row_normal, Category.values());
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(categoryAdapter);
		category.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				Category category = (Category)parent.getItemAtPosition(position);
				filterList(category);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		
		ListView listView = (ListView)findViewById(R.id.list);
		listView.setAdapter(this.adapter);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				Product product = (Product)parent.getItemAtPosition(position);
				showProductOptions(product);
				
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
	}
	
	@SuppressLint("InflateParams")
	private void selectProduct(final Product product)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(product.getName());
		builder.setCancelable(true);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.dialog_cart_item, null);
		builder.setView(layout);
		
		ImageView image = (ImageView)layout.findViewById(R.id.image);
		image.setImageResource(product.getThumbnail());
		
		final NumberPicker quantity = (NumberPicker)layout.findViewById(R.id.quantity);
		quantity.setMinValue(1);
		quantity.setMaxValue(100);
		quantity.setValue(1);
		
		builder.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				addProduct(product, quantity.getValue());
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton(R.string.button_cancel, null);
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void addProduct(Product product, int quantity)
	{
		product.setQuantity(quantity);
		product.setSelected(false);
		
		ProductDao productDao = new ProductDao();
		
		if (productDao.updateProduct(product))
		{
			this.filteredList.remove(product);
			this.totalList.remove(product);
			refreshList();
		}
	}
	
	private void showProductOptions(final Product product)
	{
		final int EDIT_PRODUCT = 0;
		final int REMOVE_PRODCUT = 1;
		
		String[] list = new String[2];
		list[EDIT_PRODUCT] = getString(R.string.button_edit);
		list[REMOVE_PRODCUT] = getString(R.string.button_remove);
		
		ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, android.R.id.text1, list)
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);
				TextView text = (TextView)view.findViewById(android.R.id.text1);
				text.setCompoundDrawablePadding(20);
				
				switch (position)
				{
					case EDIT_PRODUCT:
						text.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_edit, 0, 0, 0);
						break;
					
					case REMOVE_PRODCUT:
						text.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_delete, 0, 0, 0);
						break;
				}
				
				return view;
			}
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(product.getName());
		builder.setCancelable(true);
		builder.setAdapter(adapter, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int index)
			{
				switch (index)
				{
					case EDIT_PRODUCT:
						editProduct(product);
						break;
					
					case REMOVE_PRODCUT:
						removeProduct(product);
						break;
				}
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void newProduct()
	{
		Intent intent = new Intent(this, EditProductActivity.class);
		startActivity(intent);
	}
	
	private void editProduct(Product product)
	{
		Intent intent = new Intent(this, EditProductActivity.class);
		intent.putExtra(EditProductActivity.PARAMETER_PRODUCT, product);
		startActivity(intent);
	}
	
	private void removeProduct(Product product)
	{
		ProductDao productDao = new ProductDao();
		
		if (productDao.removeProduct(product))
		{
			this.filteredList.remove(product);
			this.totalList.remove(product);
			refreshList();
		}
		else
		{
			Toast.makeText(this, R.string.error_removing_product, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void filterList(Category category)
	{
		this.filteredList.clear();
		
		if (category != null)
		{
			Type[] types = category.getTypes();
			
			for (Product product : this.totalList)
			{
				if (product.isType(types))
				{
					this.filteredList.add(product);
				}
			}
		}
		else
		{
			this.filteredList.addAll(this.totalList);
		}
		
		sortList(this.filteredList);
		refreshList();
	}
	
	private void refreshList()
	{
		this.adapter.notifyDataSetChanged();
		
		if (this.filteredList.size() > 0)
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
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		ProductDao productDao = new ProductDao();
		this.totalList.clear();
		this.totalList.addAll(productDao.getProducts(false));
		
		Spinner categoryField = (Spinner)findViewById(R.id.category);
		Category category = (Category)categoryField.getSelectedItem();
		filterList(category);
	}
	
	private void sortList(List<Product> list)
	{
		Collections.sort(list, new Comparator<Product>()
		{
			@Override
			public int compare(Product lhs, Product rhs)
			{
				return lhs.getName().compareTo(rhs.getName());
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_add_bar, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.new_product:
				newProduct();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}