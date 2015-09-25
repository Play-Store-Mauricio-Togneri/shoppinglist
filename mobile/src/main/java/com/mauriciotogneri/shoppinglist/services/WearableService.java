package com.mauriciotogneri.shoppinglist.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.wearable.Message;
import com.mauriciotogneri.common.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.wearable.WearableConnectivity;
import com.mauriciotogneri.common.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.shoppinglist.dao.CartItemDao;
import com.mauriciotogneri.shoppinglist.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class WearableService extends Service implements WearableEvents
{
    private WearableConnectivity connectivity;

    @Override
    public void onCreate()
    {
        super.onCreate();

        connectivity = new WearableConnectivity(this, this);
        connectivity.connect();
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
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onConnectedSuccess()
    {
    }

    @Override
    public void onConnectedFail()
    {
    }

    @Override
    public void onMessageReceived(Message message)
    {
        String nodeId = message.getNodeId();
        String path = message.getPath();
        String payload = message.getPayloadAsString();

        if (TextUtils.equals(path, Paths.GET_CART))
        {
            getCart(nodeId);
        }
        else if (TextUtils.equals(path, Paths.MARK_CART_ELEMENT))
        {
            markCartElement(nodeId, payload);
        }
    }

    private void getCart(String nodeId)
    {
        CartItemDao cartItemDao = new CartItemDao();
        List<CartItem> list = cartItemDao.getCartItems();
        ArrayList<CartElement> result = new ArrayList<>();

        for (CartItem cartItem : list)
        {
            result.add(new CartElement(cartItem.getName()));
        }

        connectivity.sendMessage(Messages.resultCart(nodeId, result));
    }

    private void markCartElement(String nodeId, String payload)
    {

    }
}