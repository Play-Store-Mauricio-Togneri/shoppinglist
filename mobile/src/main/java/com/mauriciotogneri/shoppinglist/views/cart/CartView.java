package com.mauriciotogneri.shoppinglist.views.cart;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ListCartItemAdapter;
import com.mauriciotogneri.shoppinglist.model.CartItem;
import com.mauriciotogneri.shoppinglist.views.cart.CartView.UiContainer;

import java.util.List;

public class CartView extends BaseView<UiContainer> implements CartViewInterface<UiContainer>
{
    private ListCartItemAdapter listCartItemAdapter;

    @Override
    public void initialize(final Context context, final CartViewObserver observer)
    {
        listCartItemAdapter = new ListCartItemAdapter(context);

        ui.list.setAdapter(listCartItemAdapter);

        ui.list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                CartItem cartItem = (CartItem) parent.getItemAtPosition(position);
                observer.onCartItemSelected(cartItem);
            }
        });

        ui.list.setOnItemLongClickListener(new OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                CartItem cartItem = (CartItem) parent.getItemAtPosition(position);

                if (!cartItem.isSelected())
                {
                    displayCartItem(context, cartItem, observer);
                }

                return true;
            }
        });

        ui.buttonShareCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onShare();
            }
        });

        ui.buttonAddProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onAddProduct();
            }
        });
    }

    @Override
    public void removeCartItem(CartItem cartItem)
    {
        listCartItemAdapter.remove(cartItem);

        listCartItemAdapter.refresh();

        checkEmptyList();
    }

    @Override
    public void removeSelectedItems()
    {
        listCartItemAdapter.removeSelectedItems();
    }

    @Override
    public String getShareContent()
    {
        return listCartItemAdapter.getShareContent();
    }

    @Override
    public void refreshList(List<CartItem> list)
    {
        listCartItemAdapter.refresh(list);

        checkEmptyList();
    }

    private void checkEmptyList()
    {
        if (listCartItemAdapter.getCount() > 0)
        {
            ui.list.setVisibility(View.VISIBLE);
            ui.emptyLabel.setVisibility(View.GONE);
        }
        else
        {
            ui.list.setVisibility(View.GONE);
            ui.emptyLabel.setVisibility(View.VISIBLE);
        }
    }

    private void displayCartItem(Context context, CartItem cartItem, CartViewObserver observer)
    {
        // TODO: confirm to delete the cart item
        //DialogCartItem dialog = new DialogCartItem(context, cartItem.getName());
        //dialog.initialize(cartItem, observer);
        //dialog.display();
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_cart;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private final ListView list;
        private final TextView emptyLabel;
        private final TextView buttonShareCart;
        private final TextView buttonAddProduct;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.list = (ListView) findViewById(R.id.list);
            this.emptyLabel = (TextView) findViewById(R.id.empty_label);
            this.buttonShareCart = (TextView) findViewById(R.id.share_cart);
            this.buttonAddProduct = (TextView) findViewById(R.id.add_product);
        }
    }
}