package com.mauriciotogneri.shoppinglist.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.message.Message;
import com.mauriciotogneri.common.message.MessageApi.Messages;
import com.mauriciotogneri.common.message.MessageApi.Paths;
import com.mauriciotogneri.common.utils.Serializer;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.views.cart.CartView;
import com.mauriciotogneri.shoppinglist.views.cart.CartViewInterface;
import com.mauriciotogneri.shoppinglist.views.cart.CartViewObserver;
import com.mauriciotogneri.shoppinglist.wearable.WearableConnectivity;
import com.mauriciotogneri.shoppinglist.wearable.WearableConnectivity.OnDeviceNodeDetected;
import com.mauriciotogneri.shoppinglist.wearable.WearableConnectivity.WearableEvents;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity<CartViewInterface> implements WearableEvents, CartViewObserver
{
    private String nodeId = "";
    private WearableConnectivity connectivity;

    @Override
    protected void initialize()
    {
        view.initialize(this);
    }

    @Override
    public void onStubReady()
    {
        connectivity = new WearableConnectivity(this, this);
        connectivity.connect();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails)
    {
        super.onEnterAmbient(ambientDetails);

        view.onAmbientMode(true);
    }

    @Override
    public void onExitAmbient()
    {
        super.onExitAmbient();

        view.onAmbientMode(false);
    }

    @Override
    public void onConnectedSuccess()
    {
        connectivity.getDefaultDeviceNode(new OnDeviceNodeDetected()
        {
            @Override
            public void onDefaultDeviceNode(String deviceNodeId)
            {
                if (!TextUtils.isEmpty(deviceNodeId))
                {
                    nodeId = deviceNodeId;

                    connectivity.sendMessage(Messages.getCart(nodeId));
                }
                else
                {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            view.displayData(new ArrayList<CartElement>());
                            view.showToast(R.string.error_connection);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if (TextUtils.equals(message.getPath(), Paths.RESULT_CART))
        {
            List<CartElement> list = Serializer.deserialize(message.getPayload());
            view.displayData(list);
        }
    }

    @Override
    public void onCartElementSelected(CartElement cartElement)
    {
        view.markCartElement(cartElement);

        connectivity.sendMessage(Messages.markCartElement(nodeId, cartElement));
    }

    @Override
    public void onConnectedFail()
    {
        view.showToast(R.string.error_connection);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (connectivity != null)
        {
            connectivity.sendMessage(Messages.clearSelected(nodeId), new ResultCallback<SendMessageResult>()
            {
                @Override
                public void onResult(SendMessageResult sendMessageResult)
                {
                    connectivity.disconnect();
                }
            });
        }
    }

    @Override
    protected CartViewInterface getViewInstance()
    {
        return new CartView();
    }
}