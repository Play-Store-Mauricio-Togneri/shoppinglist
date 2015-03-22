package com.mauriciotogneri.shoppingcart.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;
import com.mauriciotogneri.shoppingcart.widgets.CustomTextView;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public abstract class BaseView
{
	protected View view;
	private LayoutInflater inflater;
	
	public final void init(LayoutInflater inflater, ViewGroup container)
	{
		this.inflater = inflater;
		this.view = inflater.inflate(getViewId(), container, false);
	}
	
	public View getView()
	{
		return this.view;
	}
	
	protected abstract int getViewId();
	
	protected ListView getListView(int listViewId)
	{
		return (ListView)this.view.findViewById(listViewId);
	}
	
	protected CustomTextView getCustomTextView(int textViewId)
	{
		return (CustomTextView)this.view.findViewById(textViewId);
	}
	
	protected CustomEditText getCustomEditText(int editTextId)
	{
		return (CustomEditText)this.view.findViewById(editTextId);
	}
	
	public ProductImage getProductImage(int imageId)
	{
		return (ProductImage)this.view.findViewById(imageId);
	}
	
	protected Spinner getSpinner(int spinnerId)
	{
		return (Spinner)this.view.findViewById(spinnerId);
	}
	
	protected void setButtonAction(int buttonId, View.OnClickListener callback)
	{
		View toolbarButton = this.view.findViewById(buttonId);
		toolbarButton.setOnClickListener(callback);
	}
	
	@SuppressLint("InflateParams")
	public void showToast(Context context, int messageId)
	{
		View layout = this.inflater.inflate(R.layout.custom_toast, null);
		
		TextView text = (TextView)layout.findViewById(R.id.message);
		text.setText(messageId);
		
		Toast toast = new Toast(context.getApplicationContext());
		toast.setGravity(Gravity.BOTTOM, 0, 50);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
}