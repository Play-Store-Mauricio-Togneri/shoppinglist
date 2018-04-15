package com.mauriciotogneri.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.wearable.view.WatchViewStub;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class MainActivity extends Activity implements MessageClient.OnMessageReceivedListener

{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        WatchViewStub stub = findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(stub1 -> {
            stub1.findViewById(R.id.test).setOnClickListener(v -> test());
        });
    }

    private void test()
    {
        Task<List<Node>> task = Wearable.getNodeClient(this).getConnectedNodes();
        task.addOnSuccessListener(nodes ->
        {
            for (Node node : nodes)
            {
                Wearable.getMessageClient(this).sendMessage(node.getId(), "bbb", new byte[] {1, 2, 3, 4, 5});
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
        Toast.makeText(this, messageEvent.getPath() +  messageEvent.getData().length, Toast.LENGTH_SHORT).show();
    }
}