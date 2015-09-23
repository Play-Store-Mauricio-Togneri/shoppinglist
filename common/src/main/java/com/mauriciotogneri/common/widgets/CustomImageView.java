package com.mauriciotogneri.common.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mauriciotogneri.common.utils.ImageHelper;

public class CustomImageView extends ImageView
{
    public CustomImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setImage(byte[] image, boolean grayScale)
    {
        if ((image != null) && (image.length > 0))
        {
            Bitmap bitmap = ImageHelper.getBitmapFromBytes(image);
            setImageBitmap(bitmap);

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(grayScale ? 0 : 1);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            setColorFilter(filter);
        }
    }

    public void setImage(byte[] image)
    {
        setImage(image, false);
    }
}