package com.mauriciotogneri.shoppinglist.utils;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Tracker;
import com.mauriciotogneri.shoppinglist.model.CartItem;

public class Stats
{
    private final Tracker tracker;

    public Stats(Tracker tracker)
    {
        this.tracker = tracker;
    }

    public synchronized void sendAppLaunched()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                tracker.setScreenName("App Launched");
                tracker.send(new HitBuilders.ScreenViewBuilder().build());
            }
        });
        thread.start();
    }

    public synchronized void addedCartItem(final CartItem cartItem)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                EventBuilder eventBuilder = new HitBuilders.EventBuilder();
                eventBuilder.setCategory("ADDED_PRODUCT_TO_CART");
                eventBuilder.setAction(cartItem.getCategory().getName());
                tracker.send(eventBuilder.build());
            }
        });
        thread.start();
    }
}