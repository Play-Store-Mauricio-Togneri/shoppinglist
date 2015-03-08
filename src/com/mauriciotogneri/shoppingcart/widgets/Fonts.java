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
		Fonts.loadFont(context.getAssets(), "opensans");
	}
	
	private static void loadFont(AssetManager assets, String name)
	{
		Fonts.fonts.put(name, Typeface.createFromAsset(assets, Fonts.FONTS_PATH + name + ".ttf"));
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