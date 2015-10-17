package com.mauriciotogneri.shoppinglist.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.message.Message;
import com.mauriciotogneri.common.message.MessageApi;
import com.mauriciotogneri.common.message.MessageApi.Messages;
import com.mauriciotogneri.common.message.MessageApi.Paths;
import com.mauriciotogneri.common.utils.Serializer;
import com.mauriciotogneri.shoppinglist.dao.CartItemDao;
import com.mauriciotogneri.shoppinglist.model.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WearableService extends WearableListenerService
{
    private static final int TIMEOUT = 1000 * 10; // in milliseconds

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        String nodeId = messageEvent.getSourceNodeId();
        String path = messageEvent.getPath();
        byte[] payload = messageEvent.getData();

        if (TextUtils.equals(path, Paths.GET_CART))
        {
            getCart(nodeId);
        }
        else if (TextUtils.equals(path, Paths.MARK_CART_ELEMENT))
        {
            markCartElement(payload);
        }
        else if (TextUtils.equals(path, Paths.CLEAR_SELECTED))
        {
            clearSelected();
        }
    }

    private void getCart(String nodeId)
    {
        CartItemDao cartItemDao = new CartItemDao();
        List<CartItem> list = cartItemDao.getCartItems();
        ArrayList<CartElement> result = new ArrayList<>();

        for (CartItem cartItem : list)
        {
            result.add(new CartElement(cartItem.getId(), cartItem.getName(), cartItem.getCategory().getName(), cartItem.getImage(), cartItem.isSelected()));
        }

        reply(Messages.resultCart(nodeId, result));
    }

    private void markCartElement(byte[] payload)
    {
        CartElement cartElement = Serializer.deserialize(payload);

        if (cartElement != null)
        {
            CartItemDao cartItemDao = new CartItemDao();
            cartItemDao.mark(cartElement.id, cartElement.isSelected);
        }

        updateList();
    }

    private void clearSelected()
    {
        CartItemDao cartItemDao = new CartItemDao();
        List<CartItem> list = cartItemDao.getCartItems();

        for (CartItem cartItem : list)
        {
            if (cartItem.isSelected())
            {
                cartItem.delete();
            }
        }

        updateList();
    }

    private void updateList()
    {
        Intent intent = new Intent(MessageApi.ACTION_UPDATE_LIST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void reply(final Message message)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                GoogleApiClient client = new GoogleApiClient.Builder(WearableService.this).addApi(Wearable.API).build();
                ConnectionResult connectionResult = client.blockingConnect(TIMEOUT, TimeUnit.MILLISECONDS);

                if (connectionResult.isSuccess())
                {
                    Wearable.MessageApi.sendMessage(client, message.getNodeId(), message.getPath(), message.getPayload());
                }

                client.disconnect();
            }
        }).start();
    }
}