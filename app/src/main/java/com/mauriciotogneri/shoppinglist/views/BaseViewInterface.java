package com.mauriciotogneri.shoppinglist.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import com.mauriciotogneri.shoppinglist.widgets.CustomEditText;
import com.mauriciotogneri.shoppinglist.widgets.CustomImageView;
import com.mauriciotogneri.shoppinglist.widgets.CustomTextView;

public interface BaseViewInterface
{
	View init(LayoutInflater inflater, ViewGroup container);
	
	int getViewId();
	
	ListView getListView(int listViewId);
	
	CustomTextView getCustomTextView(int textViewId);
	
	CustomEditText getCustomEditText(int editTextId);
	
	CustomImageView getCustomImageView(int imageId);
	
	Spinner getSpinner(int spinnerId);
	
	void setButtonAction(int buttonId, View.OnClickListener callback);
	
	void showToast(Context context, int messageId);
	
	void showError(Context context, int messageId);
}