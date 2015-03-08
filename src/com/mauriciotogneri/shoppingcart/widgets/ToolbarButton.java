package com.mauriciotogneri.shoppingcart.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.mauriciotogneri.shoppingcart.R;

public class ToolbarButton extends TextView
{
	public ToolbarButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs)
	{
		TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ToolbarButton);
		String fontName = styledAttributes.getString(R.styleable.ToolbarButton_font);
		styledAttributes.recycle();
		
		Typeface font = Fonts.getFont(fontName);
		setTypeface(font);
	}
}