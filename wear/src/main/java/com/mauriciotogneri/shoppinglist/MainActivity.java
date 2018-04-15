package com.mauriciotogneri.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.mauriciotogneri.common.message.Message;

import java.util.List;

public class MainActivity extends Activity implements MessageClient.OnMessageReceivedListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        requestProducts();
    }

    private void requestProducts()
    {
        Task<List<Node>> task = Wearable.getNodeClient(this).getConnectedNodes();
        task.addOnSuccessListener(nodes ->
        {
            for (Node node : nodes)
            {
                Wearable.getMessageClient(this).sendMessage(node.getId(), Message.REQUEST_PRODUCTS, new byte[0]);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Wearable.getMessageClient(this).addListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        Wearable.getMessageClient(this).removeListener(this);
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent)
    {
        Message message = new Message(messageEvent);

        if (message.action().equals(Message.RESPONSE_PRODUCTS))
        {
            Toast.makeText(this, message.payload(), Toast.LENGTH_SHORT).show();
        }
    }
}