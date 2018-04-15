package com.mauriciotogneri.shoppinglist.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.mauriciotogneri.shoppinglist.R;

import java.util.List;

public class TestActivity extends Activity implements DataClient.OnDataChangedListener, MessageClient.OnMessageReceivedListener, CapabilityClient.OnCapabilityChangedListener
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_test);

        findViewById(R.id.test).setOnClickListener(v -> sendMessage());
    }

    private void sendMessage()
    {
        Task<List<Node>> nodeListTask = Wearable.getNodeClient(this).getConnectedNodes();

        try
        {
            List<Node> nodes = Tasks.await(nodeListTask);

            for (Node node : nodes)
            {
                Task<Integer> sendMessageTask = Wearable.getMessageClient(this).sendMessage(node.getId(), "xxx", new byte[0]);
                Tasks.await(sendMessageTask);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(this);
        Wearable.getCapabilityClient(this).addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_ALL);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getCapabilityClient(this).removeListener(this);
    }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo)
    {
        System.out.println();
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer)
    {
        System.out.println();
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent)
    {
        System.out.println();
    }
}