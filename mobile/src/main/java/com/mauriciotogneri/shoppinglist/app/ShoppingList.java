package com.mauriciotogneri.shoppinglist.app;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.utils.Analytics;
import com.orm.SugarApp;

import io.fabric.sdk.android.Fabric;

public class ShoppingList extends SugarApp
{
    private static Analytics analytics;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.app_tracker);
        analytics = new Analytics(tracker);
    }

    public static Analytics analytics()
    {
        return analytics;
    }
}