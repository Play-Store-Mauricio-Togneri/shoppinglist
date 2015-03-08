package com.mauriciotogneri.shoppingcart.app;

import android.os.StrictMode;
import com.activeandroid.ActiveAndroid;
import com.mauriciotogneri.shoppingcart.widgets.Fonts;

public class ShoppingCart extends com.activeandroid.app.Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
		threadBuilder.detectAll();
		threadBuilder.penaltyLog();
		StrictMode.setThreadPolicy(threadBuilder.build());
		
		StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();
		vmBuilder.detectAll();
		vmBuilder.penaltyLog();
		StrictMode.setVmPolicy(vmBuilder.build());
		
		ActiveAndroid.initialize(this);
		Fonts.init(this);
	}
}