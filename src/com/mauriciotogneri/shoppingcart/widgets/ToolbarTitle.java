package com.mauriciotogneri.shoppingcart.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ToolbarTitle extends TextView
{
	public ToolbarTitle(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}
	
	private void init()
	{
		Typeface font = Fonts.getFont(Fonts.OPEN_SANS);
		setTypeface(font);
	}
}