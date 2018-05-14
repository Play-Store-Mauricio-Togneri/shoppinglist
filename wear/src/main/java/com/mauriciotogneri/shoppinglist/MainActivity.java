package com.mauriciotogneri.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.message.Message;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements MessageClient.OnMessageReceivedListener
{
    private CartElementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        requestProducts();
    }

    private void setProducts(List<CartElement> products)
    {
        if (!products.isEmpty())
        {
            adapter = new CartElementAdapter(this, products);

            WearableRecyclerView list = findViewById(R.id.list);
            list.setEdgeItemsCenteringEnabled(true);
            list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            list.setAdapter(adapter);
            list.setVisibility(View.VISIBLE);
        }
        else
        {
            View emptyLabel = findViewById(R.id.empty_label);
            emptyLabel.setVisibility(View.VISIBLE);
        }

        View progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
    }

    private void requestProducts()
    {
        Task<List<Node>> task = Wearable.getNodeClient(this).getConnectedNodes();
        task.addOnSuccessListener(nodes ->
        {
            for (Node node : nodes)
            {
                Message message = new Message(node.getId(), Message.REQUEST_PRODUCTS);
                message.send(this);
            }
        });
    }

    private void selectProduct(CartElement cartElement)
    {
        Task<List<Node>> task = Wearable.getNodeClient(this).getConnectedNodes();
        task.addOnSuccessListener(nodes ->
        {
            for (Node node : nodes)
            {
                Message message = new Message(node.getId(), Message.REQUEST_SELECT_PRODUCT, String.format("%s:%s", cartElement.id(), cartElement.isSelected()));
                message.send(this);
            }
        });
    }

    public void onProductSelected(CartElement cartElement)
    {
        cartElement.toggleSelection();
        adapter.sort();

        selectProduct(cartElement);
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
            Type listType = new TypeToken<ArrayList<CartElement>>()
            {
            }.getType();
            List<CartElement> products = new Gson().fromJson(message.payload(), listType);

            setProducts(products);
        }
    }
}