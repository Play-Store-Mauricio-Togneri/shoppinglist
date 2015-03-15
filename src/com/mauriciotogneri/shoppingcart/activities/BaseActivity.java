package com.mauriciotogneri.shoppingcart.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.widgets.CustomEditText;
import com.mauriciotogneri.shoppingcart.widgets.CustomTextView;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public abstract class BaseActivity extends Activity
{
	@Override
	protected final void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		init();
	}
	
	protected abstract void init();
	
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
	
	protected ListView getListView(int listViewId)
	{
		return (ListView)findViewById(listViewId);
	}
	
	protected CustomTextView getCustomTextView(int textViewId)
	{
		return (CustomTextView)findViewById(textViewId);
	}
	
	protected CustomEditText getCustomEditText(int editTextId)
	{
		return (CustomEditText)findViewById(editTextId);
	}
	
	public ProductImage getProductImage(int imageId)
	{
		return (ProductImage)findViewById(imageId);
	}
	
	protected Spinner getSpinner(int spinnerId)
	{
		return (Spinner)findViewById(spinnerId);
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
	
	protected void setButtonAction(int buttonId, View.OnClickListener callback)
	{
		View toolbarButton = findViewById(buttonId);
		toolbarButton.setOnClickListener(callback);
	}
	
	@SuppressLint("InflateParams")
	protected void showToast(int messageId)
	{
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.custom_toast, null);
		
		TextView text = (TextView)layout.findViewById(R.id.message);
		text.setText(messageId);
		
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM, 0, 50);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
}