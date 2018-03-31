package com.mauriciotogneri.shoppinglist.app;

import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mauriciotogneri.common.widgets.Fonts;
import com.mauriciotogneri.shoppinglist.BuildConfig;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.utils.Stats;
import com.orm.SugarApp;

import io.fabric.sdk.android.Fabric;

public class ShoppingList extends SugarApp
{
    private Stats stats;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG)
        {
            StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
            threadBuilder.detectAll();
            threadBuilder.penaltyLog();
            StrictMode.setThreadPolicy(threadBuilder.build());

            StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();
            vmBuilder.detectAll();
            vmBuilder.penaltyLog();
            StrictMode.setVmPolicy(vmBuilder.build());
        }

        Fonts.init(this);
    }

    public synchronized Stats getStats()
    {
        if (stats == null)
        {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker tracker = analytics.newTracker(R.xml.app_tracker);
            stats = new Stats(tracker);
        }

        return stats;
    }
}