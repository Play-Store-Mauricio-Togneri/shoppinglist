package com.mauriciotogneri.shoppingcart.widgets;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class Fonts
{
	private static final Map<String, Typeface> fonts = new HashMap<String, Typeface>();
	private static final String FONTS_PATH = "fonts/";
	
	public static void init(Context context)
	{
		Fonts.loadFont(context.getAssets(), "opensans", "0");
		Fonts.loadFont(context.getAssets(), "glyphicons", "1");
		Fonts.loadFont(context.getAssets(), "fontawesome", "2");
		Fonts.loadFont(context.getAssets(), "icomoon", "3");
	}
	
	private static void loadFont(AssetManager assets, String name, String key)
	{
		Fonts.fonts.put(key, Typeface.createFromAsset(assets, Fonts.FONTS_PATH + name + ".ttf"));
	}
	
	public static Typeface getFont(String name, int style)
	{
		Typeface fontFamily = Fonts.fonts.get(name);
		
		return Typeface.create(fontFamily, style);
	}
	
	public static Typeface getFont(String name)
	{
		return Fonts.getFont(name, Typeface.NORMAL);
	}
}