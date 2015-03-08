package com.mauriciotogneri.shoppingcart.activities;

import java.io.ByteArrayOutputStream;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.activeandroid.Model;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.CartItemAdapter;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;

public class MainActivity extends Activity
{
	private CartItemAdapter cartItemAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		this.cartItemAdapter = new CartItemAdapter(this);
		
		ListView listView = (ListView)findViewById(R.id.cart_list);
		listView.setAdapter(this.cartItemAdapter);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				CartItem cartItem = (CartItem)parent.getItemAtPosition(position);
				
				if (!cartItem.isSelected())
				{
					displayCartItem(cartItem);
				}
				
				return true;
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				CartItem cartItem = (CartItem)parent.getItemAtPosition(position);
				selectCartItem(cartItem);
			}
		});
		
		TextView addProduct = (TextView)findViewById(R.id.add_product);
		addProduct.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				addProduct();
			}
		});
		
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// intent.setType("image/*");
		// startActivityForResult(intent, 123);
		
		// initDatabase();
	}
	
	private void initDatabase()
	{
		Category drinks = new Category("Drinks");
		drinks.save();
		
		Category food = new Category("Food");
		food.save();
		
		Category kitchen = new Category("Kitchen");
		kitchen.save();
		
		// --------------------------------
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.product_bananas);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bitmapdata = stream.toByteArray();
		
		Product cocaCola = new Product("Coca-Cola", drinks, bitmapdata);
		cocaCola.save();
		
		Product milk = new Product("Milk", drinks, bitmapdata);
		milk.save();
		
		Product bananas = new Product("Bananas", food, bitmapdata);
		bananas.save();
		
		Product cereals = new Product("Cereals", food, bitmapdata);
		cereals.save();
		
		Product bag = new Product("Bag", kitchen, bitmapdata);
		bag.save();
		
		// --------------------------------
		
		CartItem bananas1 = new CartItem(bananas, 12, false);
		bananas1.save();
		
		CartItem bag1 = new CartItem(bag, 34, false);
		bag1.save();
	}
	
	private void selectCartItem(CartItem cartItem)
	{
		cartItem.invertSelection();
		cartItem.save();
		
		updateList(true);
	}
	
	private void updateList(boolean sort)
	{
		this.cartItemAdapter.update(sort);
		
		ListView listView = (ListView)findViewById(R.id.cart_list);
		TextView emptyLabel = (TextView)findViewById(R.id.empty_label);
		
		if (this.cartItemAdapter.getCount() > 0)
		{
			listView.setVisibility(View.VISIBLE);
			emptyLabel.setVisibility(View.GONE);
		}
		else
		{
			listView.setVisibility(View.GONE);
			emptyLabel.setVisibility(View.VISIBLE);
		}
	}
	
	@SuppressLint("InflateParams")
	private void displayCartItem(final CartItem cartItem)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(cartItem.getName());
		builder.setCancelable(true);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.dialog_cart_item, null);
		builder.setView(layout);
		
		ImageView thumbnail = (ImageView)layout.findViewById(R.id.thumbnail);
		byte[] picture = cartItem.getPicture();
		
		if ((picture != null) && (picture.length > 0))
		{
			Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
			thumbnail.setImageBitmap(bitmap);
		}
		
		final NumberPicker quantity = (NumberPicker)layout.findViewById(R.id.quantity);
		quantity.setMinValue(1);
		quantity.setMaxValue(100);
		quantity.setValue(cartItem.getQuantity());
		
		builder.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				updateQuantity(cartItem, quantity.getValue());
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton(R.string.button_cancel, null);
		
		builder.setNeutralButton(R.string.button_remove, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				removeCartItem(cartItem);
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void updateQuantity(CartItem cartItem, int quantity)
	{
		cartItem.setQuantity(quantity);
		cartItem.save();
		
		updateList(false);
	}
	
	private void removeCartItem(CartItem cartItem)
	{
		cartItem.delete();
		
		this.cartItemAdapter.remove(cartItem);
		updateList(false);
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
		
		List<CartItem> cartItems = Model.all(CartItem.class);
		
		this.cartItemAdapter.clear();
		this.cartItemAdapter.addAll(cartItems);
		updateList(true);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		this.cartItemAdapter.removeSelectedItems();
	}
}