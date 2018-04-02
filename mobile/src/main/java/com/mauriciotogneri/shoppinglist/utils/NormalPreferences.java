package com.mauriciotogneri.shoppinglist.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.mauriciotogneri.androidutils.Preferences;

public class NormalPreferences extends Preferences
{
    private static final String FIELD_LOCALE = "locale";

    public NormalPreferences(Context context)
    {
        super(PreferenceManager.getDefaultSharedPreferences(context));
    }

    // =============================================================================================

    public void locale(String locale)
    {
        save(FIELD_LOCALE, locale);
    }

    public String locale()
    {
        return load(FIELD_LOCALE, "");
    }
}