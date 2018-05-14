package com.mauriciotogneri.shoppinglist.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.mauriciotogneri.androidutils.Preferences;

public class NormalPreferences extends Preferences
{
    private static final String FIELD_MIGRATION_DONE = "migration.done";

    public NormalPreferences(Context context)
    {
        super(PreferenceManager.getDefaultSharedPreferences(context));
    }

    // =============================================================================================

    public void migrationDone()
    {
        save(FIELD_MIGRATION_DONE, true);
    }

    public Boolean isMigrationDone()
    {
        return load(FIELD_MIGRATION_DONE, false);
    }
}