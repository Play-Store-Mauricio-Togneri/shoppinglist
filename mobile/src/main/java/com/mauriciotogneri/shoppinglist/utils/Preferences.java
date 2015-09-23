package com.mauriciotogneri.shoppinglist.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences
{
    private final SharedPreferences sharedPreferences;

    private static Preferences instance;

    private static final String KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH";

    private Preferences(Context context)
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized Preferences getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new Preferences(context);
        }

        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    private void save(String key, boolean value)
    {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    private boolean get(String key, boolean defaultValue)
    {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public synchronized boolean isFirstLaunch()
    {
        return get(KEY_FIRST_LAUNCH, true);
    }

    public synchronized void setFirstLaunch(boolean value)
    {
        save(KEY_FIRST_LAUNCH, value);
    }
}