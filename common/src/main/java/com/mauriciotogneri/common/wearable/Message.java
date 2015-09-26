package com.mauriciotogneri.common.wearable;

public class Message
{
    private final String nodeId;
    private final String path;
    private final byte[] payload;

    public Message(String nodeId, String path, byte[] payload)
    {
        this.nodeId = nodeId;
        this.path = path;
        this.payload = payload;
    }

    public Message(String nodeId, String path)
    {
        this(nodeId, path, null);
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public String getPath()
    {
        return path;
    }

    public byte[] getPayload()
    {
        return payload;
    }
}