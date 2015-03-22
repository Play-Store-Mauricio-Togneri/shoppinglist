package com.mauriciotogneri.shoppingcart.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import com.mauriciotogneri.shoppingcart.views.BaseView;

public abstract class BaseActivity<V extends BaseView> extends Activity
{
	protected V view;
	
	@Override
	protected final void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		try
		{
			this.view = getViewClass().newInstance();
			this.view.init(getLayoutInflater(), null);
			setContentView(this.view.getView());
			initialize();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected final void onDestroy()
	{
		onFinish();
		this.view = null;
		
		super.onDestroy();
	}
	
	protected abstract Class<V> getViewClass();
	
	protected void initialize()
	{
	}
	
	protected void onFinish()
	{
	}
	
	@SuppressWarnings("unchecked")
	protected <Type> Type getParameter(String key, Type defaultValue)
	{
		Bundle extras = getIntent().getExtras();
		
		if ((extras != null) && extras.containsKey(key))
		{
			return (Type)getIntent().getExtras().get(key);
		}
		else
		{
			return defaultValue;
		}
	}
	
	protected void startActivity(Class<?> cls)
	{
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}
	
	protected void share(int titleId, String content)
	{
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, content);
		shareIntent.setType("text/plain");
		startActivity(Intent.createChooser(shareIntent, getResources().getText(titleId)));
	}
	
	protected byte[] getImageFromBitmap(Bitmap bitmap)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		
		return stream.toByteArray();
	}
	
	protected byte[] getImageFromBitmap(int resourceId)
	{
		return getImageFromBitmap(BitmapFactory.decodeResource(getResources(), resourceId));
	}
	
	protected byte[] getBytesFromUri(Uri uri) throws IOException
	{
		InputStream inputStream = getContentResolver().openInputStream(uri);
		byte[] inputData = getBytes(inputStream);
		
		inputStream.close();
		
		return inputData;
	}
	
	private byte[] getBytes(InputStream inputStream) throws IOException
	{
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		
		int read = 0;
		
		while ((read = inputStream.read(buffer)) != -1)
		{
			byteBuffer.write(buffer, 0, read);
		}
		
		return byteBuffer.toByteArray();
	}
	
	protected Bitmap getResizedBitmap(byte[] image, int width, int height)
	{
		Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
		
		if ((bitmap.getWidth() != width) || (bitmap.getHeight() != height))
		{
			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		}
		
		return bitmap;
	}
}