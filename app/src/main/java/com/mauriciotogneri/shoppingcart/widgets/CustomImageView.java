package com.mauriciotogneri.shoppingcart.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

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
			Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
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