package com.mauriciotogneri.shoppinglist.utils;

import android.content.Context;

public class ColorHelper
{
    public static String getColorAsHex(Context context, int colorId)
    {
        int color = getColorAsInt(context, colorId);

        return "#" + Integer.toHexString(color);
    }

    public static int getColorAsInt(Context context, int colorId)
    {
        return context.getResources().getColor(colorId);
    }
}