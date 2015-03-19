package com.mauriciotogneri.shoppingcart.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.ListCartItemAdapter;
import com.mauriciotogneri.shoppingcart.model.CartItem;
import com.mauriciotogneri.shoppingcart.utils.DatabaseInitializer;
import com.mauriciotogneri.shoppingcart.widgets.CustomDialog;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class CartActivity extends BaseActivity
{
	private ListCartItemAdapter listCartItemAdapter;
	
	private static final String ATTRIBUTE_FIRST_LAUNCH = "first_launch";
	
	@Override
	protected void init()
	{
		setContentView(R.layout.activity_cart);
		
		this.listCartItemAdapter = new ListCartItemAdapter(this);
		
		ListView listView = getListView(R.id.cart_list);
		listView.setAdapter(this.listCartItemAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				CartItem cartItem = (CartItem)parent.getItemAtPosition(position);
				selectCartItem(cartItem);
			}
		});
		
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
		
		setButtonAction(R.id.share_cart, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				shareCart();
			}
		});
		
		setButtonAction(R.id.add_product, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				addProduct();
			}
		});
	}
	
	private void shareCart()
	{
		if (!this.listCartItemAdapter.isEmpty())
		{
			share(R.string.label_share_cart, this.listCartItemAdapter.getShareContent());
		}
		else
		{
			showToast(R.string.error_cart_empty);
		}
	}
	
	private void selectCartItem(CartItem cartItem)
	{
		cartItem.invertSelection();
		cartItem.save();
		
		updateList(true);
	}
	
	private void updateList(boolean sort)
	{
		this.listCartItemAdapter.refresh(sort);
		
		ListView listView = getListView(R.id.cart_list);
		TextView emptyLabel = getCustomTextView(R.id.empty_label);
		
		if (this.listCartItemAdapter.getCount() > 0)
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
		CustomDialog dialog = new CustomDialog(this, cartItem.getName());
		dialog.setLayout(R.layout.dialog_cart_item);
		
		ProductImage productImage = dialog.getProductImage(R.id.thumbnail);
		productImage.setImage(cartItem.getImage());
		
		final NumberPicker quantity = dialog.getNumberPicker(R.id.quantity);
		quantity.setMinValue(1);
		quantity.setMaxValue(100);
		quantity.setValue(cartItem.getQuantity());
		
		dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				updateQuantity(cartItem, quantity.getValue());
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.setNeutralButton(R.string.button_remove, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				removeCartItem(cartItem);
			}
		});
		
		dialog.display();
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
		
		this.listCartItemAdapter.remove(cartItem);
		updateList(false);
	}
	
	private void addProduct()
	{
		startActivity(AddProductActivity.class);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		updateList(true);
		
		SharedPreferences preferencces = PreferenceManager.getDefaultSharedPreferences(this);
		
		if (preferencces.getBoolean(CartActivity.ATTRIBUTE_FIRST_LAUNCH, true))
		{
			DatabaseInitializer databaseInitializer = new DatabaseInitializer(this);
			databaseInitializer.execute();
			
			preferencces.edit().putBoolean(CartActivity.ATTRIBUTE_FIRST_LAUNCH, false).commit();
		}
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		this.listCartItemAdapter.removeSelectedItems();
	}
}