package com.mauriciotogneri.shoppingcart.widgets;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;

public class CustomDialog extends AlertDialog.Builder
{
	@SuppressLint("InflateParams")
	public CustomDialog(Context context, String title)
	{
		super(context);
		
		LinearLayout customTitle = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
		TextView dialogTitle = (TextView)customTitle.findViewById(R.id.title);
		dialogTitle.setText(title);
		
		setCustomTitle(customTitle);
		setCancelable(true);
	}
	
	public void display()
	{
		AlertDialog dialog = create();
		dialog.show();
		
		Button buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
		
		if (buttonNegative != null)
		{
			buttonNegative.setTypeface(Fonts.getFont(Fonts.OPEN_SANS));
		}
		
		Button buttonNeutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
		
		if (buttonNeutral != null)
		{
			buttonNeutral.setTypeface(Fonts.getFont(Fonts.OPEN_SANS));
		}
		
		Button buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		
		if (buttonPositive != null)
		{
			buttonPositive.setTypeface(Fonts.getFont(Fonts.OPEN_SANS));
		}
	}
}