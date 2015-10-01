package com.mauriciotogneri.shoppinglist.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.mauriciotogneri.common.base.BaseFragment;
import com.mauriciotogneri.common.message.MessageApi;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.dao.CartItemDao;
import com.mauriciotogneri.shoppinglist.model.CartItem;
import com.mauriciotogneri.shoppinglist.model.DatabaseInitializer;
import com.mauriciotogneri.shoppinglist.utils.Preferences;
import com.mauriciotogneri.shoppinglist.views.cart.CartView;
import com.mauriciotogneri.shoppinglist.views.cart.CartViewInterface;
import com.mauriciotogneri.shoppinglist.views.cart.CartViewObserver;

import java.util.List;

public class CartFragment extends BaseFragment<CartViewInterface> implements CartViewObserver
{
    private BroadcastReceiver receiver;

    @Override
    protected void initialize()
    {
        view.initialize(getContext(), this);

        receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                reloadList();
            }
        };

        Preferences preferences = Preferences.getInstance(getContext());

        if (preferences.isFirstLaunch())
        {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer(getContext());
            databaseInitializer.execute();
        }
    }

    @Override
    public void onShare()
    {
        String shareContent = view.getShareContent();

        if (!shareContent.isEmpty())
        {
            share(R.string.label_share_cart, shareContent);
        }
        else
        {
            view.showToast(R.string.error_cart_empty);
        }
    }

    private synchronized void reloadList()
    {
        CartItemDao cartItemDao = new CartItemDao();
        List<CartItem> list = cartItemDao.getCartItems();

        view.fillList(list);
    }

    @Override
    public void onCartItemSelected(CartItem cartItem)
    {
        cartItem.invertSelection();
        cartItem.save();

        reloadList();
    }

    @Override
    public void onCartItemRemoved(CartItem cartItem)
    {
        cartItem.delete();

        view.removeCartItem(cartItem);
    }

    @Override
    public void onAddProduct()
    {
        AddProductFragment fragment = new AddProductFragment();
        startFragment(fragment);
    }

    @Override
    public void onActivate()
    {
        reloadList();
    }

    @Override
    protected void onFinish()
    {
        view.removeSelectedItems();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        reloadList();

        if (receiver != null)
        {
            IntentFilter filter = new IntentFilter(MessageApi.ACTION_UPDATE_LIST);
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
        }
    }

    @Override
    public void onPause()
    {
        if (receiver != null)
        {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        }

        super.onPause();
    }

    @Override
    protected CartViewInterface getViewInstance()
    {
        return new CartView();
    }
}