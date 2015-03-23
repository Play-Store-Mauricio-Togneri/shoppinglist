package com.mauriciotogneri.shoppingcart.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.mauriciotogneri.shoppingcart.widgets.CustomDialog;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class CartView extends BaseView
{
	private ListCartItemAdapter listCartItemAdapter;
	
	public void initialize(final Context context, final Observer observer)
	{
		this.listCartItemAdapter = new ListCartItemAdapter(context);
		
		ListView listView = getListView(R.id.cart_list);
		listView.setAdapter(this.listCartItemAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				CartItem cartItem = (CartItem)parent.getItemAtPosition(position);
				observer.onCartItemSelected(cartItem);
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
					displayCartItem(context, cartItem, observer);
				}
				
				return true;
			}
		});
		
		setButtonAction(R.id.share_cart, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				observer.onShare();
			}
		});
		
		setButtonAction(R.id.add_product, new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				observer.onAddProduct();
			}
		});
	}
	
	public void removeCartItem(CartItem cartItem)
	{
		this.listCartItemAdapter.remove(cartItem);
	}
	
	public void removeSelectedItems()
	{
		this.listCartItemAdapter.removeSelectedItems();
	}
	
	public String getShareContent()
	{
		return this.listCartItemAdapter.getShareContent();
	}
	
	public void refreshList(boolean sort)
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
	
	private void displayCartItem(Context context, final CartItem cartItem, final Observer observer)
	{
		CustomDialog dialog = new CustomDialog(context, cartItem.getName());
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
				observer.onQuantityUpdated(cartItem, quantity.getValue());
			}
		});
		
		dialog.setNegativeButton(R.string.button_cancel, null);
		
		dialog.setNeutralButton(R.string.button_remove, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				observer.onCartItemRemoved(cartItem);
			}
		});
		
		dialog.display();
	}
	
	@Override
	protected int getViewId()
	{
		return R.layout.fragment_cart;
	}
	
	public interface Observer
	{
		void onCartItemSelected(CartItem cartItem);
		
		void onShare();
		
		void onAddProduct();
		
		void onQuantityUpdated(CartItem cartItem, int value);
		
		void onCartItemRemoved(CartItem cartItem);
	}
}