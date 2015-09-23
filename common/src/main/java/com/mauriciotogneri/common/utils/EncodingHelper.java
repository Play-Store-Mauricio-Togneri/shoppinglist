package com.mauriciotogneri.common.utils;

import android.text.TextUtils;

public class EncodingHelper
{
    private static final String UTF_8 = "UTF-8";

    private EncodingHelper()
    {
    }

    public static byte[] getStringAsBytes(String input)
    {
        if (TextUtils.isEmpty(input))
        {
            return new byte[0];
        }
        else
        {
            try
            {
                return input.getBytes(EncodingHelper.UTF_8);
            }
            catch (Exception e)
            {
                return new byte[0];
            }
        }
    }

    public static String getBytesAsString(byte[] data)
    {
        if ((data == null) || (data.length == 0))
        {
            return null;
        }
        else
        {
            try
            {
                return new String(data, EncodingHelper.UTF_8);
            }
            catch (Exception e)
            {
                return null;
            }
        }
    }
}