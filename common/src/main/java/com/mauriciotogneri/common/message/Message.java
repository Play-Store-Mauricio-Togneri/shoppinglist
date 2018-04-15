package com.mauriciotogneri.common.message;

import android.content.Context;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.nio.charset.Charset;

public class Message
{
    private final String nodeId;
    private final String action;
    private final String payload;

    public static final String REQUEST_PRODUCTS = "request.products";
    public static final String RESPONSE_PRODUCTS = "response.products";

    public Message(String nodeId, String action, String payload)
    {
        this.nodeId = nodeId;
        this.action = action;
        this.payload = payload;
    }

    public Message(MessageEvent messageEvent)
    {
        this.nodeId = messageEvent.getSourceNodeId();
        this.action = messageEvent.getPath();
        this.payload = new String(messageEvent.getData(), Charset.forName("UTF-8"));
    }

    public String nodeId()
    {
        return nodeId;
    }

    public String action()
    {
        return action;
    }

    public String payload()
    {
        return payload;
    }

    public void send(Context context)
    {
        Wearable.getMessageClient(context).sendMessage(nodeId, action, payload.getBytes(Charset.forName("utf-8")));
    }
}