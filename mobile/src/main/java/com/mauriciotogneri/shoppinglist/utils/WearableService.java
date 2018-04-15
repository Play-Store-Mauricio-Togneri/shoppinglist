package com.mauriciotogneri.shoppinglist.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.mauriciotogneri.common.message.Message;

public class WearableService extends Service implements MessageClient.OnMessageReceivedListener
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Wearable.getMessageClient(this).addListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return Service.START_STICKY;
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent)
    {
        Message message = new Message(messageEvent);

        if (message.action().equals(Message.REQUEST_PRODUCTS))
        {
            Message response = new Message(message.nodeId(), Message.RESPONSE_PRODUCTS, "THIS ARE THE PRODUCTS");
            response.send(this);
        }
    }
}