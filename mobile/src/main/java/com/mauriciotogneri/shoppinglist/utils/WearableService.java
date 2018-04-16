package com.mauriciotogneri.shoppinglist.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.message.Message;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.tasks.product.LoadProductsInCart;

import java.util.ArrayList;
import java.util.List;

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
            returnProducts(message.nodeId());
        }
        else if (message.action().equals(Message.REQUEST_SELECT_PRODUCT))
        {
            Toast.makeText(this, message.payload(), Toast.LENGTH_SHORT).show();
        }
    }

    private void returnProducts(String nodeId)
    {
        LoadProductsInCart task = new LoadProductsInCart(this, products ->
        {
            List<CartElement> cartElements = new ArrayList<>();

            for (Product product : products)
            {
                cartElements.add(product.cartElement());
            }

            Message response = new Message(nodeId, Message.RESPONSE_PRODUCTS, new Gson().toJson(cartElements));
            response.send(this);
        });
        task.execute();
    }
}