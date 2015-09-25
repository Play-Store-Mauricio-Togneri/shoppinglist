package com.mauriciotogneri.shoppinglist.views.cart;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseDialog;
import com.mauriciotogneri.common.base.BaseDialog.OnAccept;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.CartAdapter;
import com.mauriciotogneri.shoppinglist.model.CartItem;
import com.mauriciotogneri.shoppinglist.views.cart.CartView.UiContainer;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogConfirmation;

import java.util.List;

public class CartView extends BaseView<UiContainer> implements CartViewInterface<UiContainer>
{
    private CartAdapter adapter;

    @Override
    public void initialize(final Context context, final CartViewObserver observer)
    {
        adapter = new CartAdapter(context);

        ui.list.setAdapter(adapter);

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
                    removeCartItem(context, cartItem, observer);
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
        adapter.remove(cartItem);
        adapter.update();

        checkEmptyList();
    }

    @Override
    public void removeSelectedItems()
    {
        adapter.removeSelectedItems();
    }

    @Override
    public String getShareContent()
    {
        return adapter.getShareContent();
    }

    @Override
    public void fillList(List<CartItem> list)
    {
        adapter.update(list);

        checkEmptyList();
    }

    private void checkEmptyList()
    {
        if (adapter.getCount() > 0)
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

    private void removeCartItem(Context context, final CartItem cartItem, final CartViewObserver observer)
    {
        DialogConfirmation dialog = new DialogConfirmation(context, cartItem.getName());
        dialog.initialize(R.string.confirmation_remove_car_item, new OnAccept()
        {
            @Override
            public void onAccept(BaseDialog dialog)
            {
                dialog.close();
                observer.onCartItemRemoved(cartItem);
            }
        });
        dialog.display();
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