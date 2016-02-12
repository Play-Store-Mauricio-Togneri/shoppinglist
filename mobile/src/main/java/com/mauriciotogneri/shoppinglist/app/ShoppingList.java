package com.mauriciotogneri.shoppinglist.app;

import android.os.StrictMode;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mauriciotogneri.common.widgets.Fonts;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.utils.Stats;
import com.orm.SugarApp;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formUri = "http://zeronest.com/acra/report.php")
public class ShoppingList extends SugarApp
{
    private Stats stats;

    @Override
    public void onCreate()
    {
        super.onCreate();

        ACRA.init(this);
        ACRA.getErrorReporter().putCustomData("PACKAGE_NAME", getPackageName());

        StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
        threadBuilder.detectAll();
        threadBuilder.penaltyLog();
        StrictMode.setThreadPolicy(threadBuilder.build());

        StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();
        vmBuilder.detectAll();
        vmBuilder.penaltyLog();
        StrictMode.setVmPolicy(vmBuilder.build());

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