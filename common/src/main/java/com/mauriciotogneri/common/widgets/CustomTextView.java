package com.mauriciotogneri.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mauriciotogneri.common.R;

public class CustomTextView extends TextView
{
    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String fontName = styledAttributes.getString(R.styleable.CustomTextView_font);
        styledAttributes.recycle();

        if (!TextUtils.isEmpty(fontName))
        {
            Typeface font = Fonts.getFont(fontName);
            setTypeface(font);
        }
    }
}