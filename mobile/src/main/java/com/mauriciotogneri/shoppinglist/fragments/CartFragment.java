package com.mauriciotogneri.shoppinglist.fragments;

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
    @Override
    protected void initialize()
    {
        view.initialize(getContext(), this);
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

    private void reloadList()
    {
        CartItemDao cartItemDao = new CartItemDao();
        List<CartItem> list = cartItemDao.getCartItems();

        view.refreshList(list);
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

        Preferences preferences = Preferences.getInstance(getContext());

        if (preferences.isFirstLaunch())
        {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer(getContext());
            databaseInitializer.execute();
        }
    }

    @Override
    protected void onFinish()
    {
        view.removeSelectedItems();
    }

    @Override
    protected CartViewInterface getViewInstance()
    {
        return new CartView();
    }
}