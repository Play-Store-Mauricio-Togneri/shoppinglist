package com.mauriciotogneri.common.wearable;

import com.mauriciotogneri.common.utils.EncodingHelper;

public class Message
{
    private final String nodeId;
    private final String path;
    private final String payload;

    public Message(String nodeId, String path, String payload)
    {
        this.nodeId = nodeId;
        this.path = path;
        this.payload = payload;
    }

    public Message(String nodeId, String path, byte[] payload)
    {
        this.nodeId = nodeId;
        this.path = path;
        this.payload = EncodingHelper.getBytesAsString(payload);
    }

    public Message(String nodeId, String path)
    {
        this(nodeId, path, "");
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public String getPath()
    {
        return path;
    }

    public String getPayloadAsString()
    {
        return payload;
    }

    public byte[] getPayloadAsBytes()
    {
        return EncodingHelper.getStringAsBytes(payload);
    }
}