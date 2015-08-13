package com.mauriciotogneri.shoppinglist.widgets;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class CustomTypefaceSpan extends TypefaceSpan
{
    private final Typeface type;

    public CustomTypefaceSpan(Typeface type)
    {
        super("");

        this.type = type;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds)
    {
        applyCustomTypeFace(ds, type);
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint paint)
    {
        applyCustomTypeFace(paint, type);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf)
    {
        int oldStyle;
        Typeface old = paint.getTypeface();

        if (old == null)
        {
            oldStyle = 0;
        }
        else
        {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();

        if ((fake & Typeface.BOLD) != 0)
        {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0)
        {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}