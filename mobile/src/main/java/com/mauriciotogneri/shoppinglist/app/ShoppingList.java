package com.mauriciotogneri.shoppinglist.app;

import com.crashlytics.android.Crashlytics;
import com.orm.SugarApp;

import io.fabric.sdk.android.Fabric;

public class ShoppingList extends SugarApp
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        // TODO: remove
        /*new Thread(() ->
        {
            AppDatabase database = AppDatabase.instance(this);
            database.initialize(this);
        }).start();*/
    }
}