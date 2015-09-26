package com.mauriciotogneri.shoppinglist.activities;

import android.text.TextUtils;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.utils.Serializer;
import com.mauriciotogneri.common.wearable.Message;
import com.mauriciotogneri.common.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.wearable.WearableConnectivity;
import com.mauriciotogneri.common.wearable.WearableConnectivity.OnDeviceNodeDetected;
import com.mauriciotogneri.common.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.views.cart.CartView;
import com.mauriciotogneri.shoppinglist.views.cart.CartViewInterface;
import com.mauriciotogneri.shoppinglist.views.cart.CartViewObserver;

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
    public void onConnectedSuccess()
    {
        connectivity.getDefaultDeviceNode(new OnDeviceNodeDetected()
        {
            @Override
            public void onDefaultDeviceNode(String deviceNodeId)
            {
                nodeId = deviceNodeId;

                connectivity.sendMessage(Messages.getCart(nodeId));
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