package com.mauriciotogneri.shoppingcart.activities;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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
		
		// updateList(true);
		
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// intent.setType("image/*");
		// startActivityForResult(intent, 123);
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
		
		if (this.cartItemAdapter.getCount() > 0)
		{
			ListView listView = (ListView)findViewById(R.id.cart_list);
			listView.setVisibility(View.VISIBLE);
			
			TextView emptyLabel = (TextView)findViewById(R.id.empty_label);
			emptyLabel.setVisibility(View.GONE);
		}
		else
		{
			ListView listView = (ListView)findViewById(R.id.cart_list);
			listView.setVisibility(View.GONE);
			
			TextView emptyLabel = (TextView)findViewById(R.id.empty_label);
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
		
		ImageView image = (ImageView)layout.findViewById(R.id.image);
		// image.setImageResource(cartItem.getPicture());
		
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
		cartItem.setQuantity(0);
		cartItem.setSelected(false);
		cartItem.save();
		
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
		
		int limit = this.cartItemAdapter.getCount();
		
		for (int i = 0; i < limit; i++)
		{
			CartItem cartItem = this.cartItemAdapter.getItem(i);
			
			if (cartItem.isSelected())
			{
				cartItem.setQuantity(0);
				cartItem.setSelected(false);
				cartItem.save();
			}
		}
	}
}