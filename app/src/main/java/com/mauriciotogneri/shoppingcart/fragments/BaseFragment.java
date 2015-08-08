package com.mauriciotogneri.shoppingcart.fragments;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mauriciotogneri.shoppingcart.activities.MainActivity;
import com.mauriciotogneri.shoppingcart.views.BaseViewInterface;

public abstract class BaseFragment<V extends BaseViewInterface> extends Fragment
{
	protected MainActivity activity;
	protected V view;
	private Object result = null;
	
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		this.view = getViewInstance();
		
		return this.view.init(inflater, container);
	}
	
	@Override
	public final void onDestroyView()
	{
		onFinish();
		this.view = null;
		
		super.onDestroyView();
	}
	
	protected abstract V getViewInstance();
	
	protected void onFinish()
	{
	}
	
	protected void initialize()
	{
	}
	
	public void onActivate()
	{
	}
	
	public void onActivate(@SuppressWarnings("unused") Object result)
	{
	}
	
	protected Context getContext()
	{
		return this.activity;
	}
	
	@Override
	public final void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		initialize();
		onActivate();
	}
	
	@Override
	public final void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public final void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		this.activity = (MainActivity)activity;
	}
	
	@SuppressWarnings("unchecked")
	protected <Type> Type getParameter(String key, Type defaultValue)
	{
		Bundle extras = getArguments();
		
		if ((extras != null) && extras.containsKey(key))
		{
			return (Type)extras.get(key);
		}
		else
		{
			return defaultValue;
		}
	}
	
	protected void startFragment(BaseFragment<?> fragment)
	{
		this.activity.addFragment(fragment);
	}
	
	protected void close()
	{
		this.activity.removeFragment();
	}
	
	protected void setResult(Object result)
	{
		this.result = result;
	}
	
	public Object getResult()
	{
		return this.result;
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
		InputStream inputStream = this.activity.getContentResolver().openInputStream(uri);
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