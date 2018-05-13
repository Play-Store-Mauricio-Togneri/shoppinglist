package com.mauriciotogneri.shoppinglist.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.message.Message;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.tasks.product.LoadProductsInCart;
import com.mauriciotogneri.shoppinglist.tasks.product.UpdateProducts;

import java.util.ArrayList;
import java.util.List;

public class WearableService extends Service implements MessageClient.OnMessageReceivedListener
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getMessageClient(this).addListener(this);

        return START_STICKY;
    }

    public void onDestroy()
    {
        startService(new Intent(this, getClass()));
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
            selectProduct(message.payload());
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

    private void selectProduct(String payload)
    {
        try
        {
            String[] parts = payload.split(":");
            Integer productId = Integer.parseInt(parts[0]);
            Boolean selected = Boolean.parseBoolean(parts[1]);

            UpdateProducts task = new UpdateProducts(this);
            task.setSelection(productId, selected);
        }
        catch (Exception e)
        {
            // ignore
        }
    }
}