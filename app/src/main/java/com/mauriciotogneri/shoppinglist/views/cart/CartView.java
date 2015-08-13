package com.mauriciotogneri.shoppinglist.views.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ListCartItemAdapter;
import com.mauriciotogneri.shoppinglist.model.CartItem;
import com.mauriciotogneri.shoppinglist.views.BaseView;
import com.mauriciotogneri.shoppinglist.widgets.CustomDialog;
import com.mauriciotogneri.shoppinglist.widgets.CustomImageView;

import java.util.List;

public class CartView extends BaseView implements CartViewInterface
{
    private ListCartItemAdapter listCartItemAdapter;

    @Override
    public void initialize(final Context context, final CartViewObserver observer)
    {
        this.listCartItemAdapter = new ListCartItemAdapter(context);

        ListView listView = getListView(R.id.cart_list);
        listView.setAdapter(this.listCartItemAdapter);

        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                CartItem cartItem = (CartItem) parent.getItemAtPosition(position);
                observer.onCartItemSelected(cartItem);
            }
        });

        listView.setOnItemLongClickListener(new OnItemLongClickListener()
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

        setButtonAction(R.id.share_cart, new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onShare();
            }
        });

        setButtonAction(R.id.add_product, new View.OnClickListener()
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
        this.listCartItemAdapter.remove(cartItem);
    }

    @Override
    public void removeSelectedItems()
    {
        this.listCartItemAdapter.removeSelectedItems();
    }

    @Override
    public String getShareContent()
    {
        return this.listCartItemAdapter.getShareContent();
    }

    @Override
    public void refreshList(List<CartItem> list)
    {
        this.listCartItemAdapter.refresh(list);

        checkEmptyList();
    }

    @Override
    public void refreshList()
    {
        this.listCartItemAdapter.refresh();

        checkEmptyList();
    }

    private void checkEmptyList()
    {
        ListView listView = getListView(R.id.cart_list);
        TextView emptyLabel = getCustomTextView(R.id.empty_label);

        if (this.listCartItemAdapter.getCount() > 0)
        {
            listView.setVisibility(View.VISIBLE);
            emptyLabel.setVisibility(View.GONE);
        }
        else
        {
            listView.setVisibility(View.GONE);
            emptyLabel.setVisibility(View.VISIBLE);
        }
    }

    private void displayCartItem(Context context, final CartItem cartItem, final CartViewObserver observer)
    {
        CustomDialog dialog = new CustomDialog(context, cartItem.getName());
        dialog.setLayout(R.layout.dialog_cart_item);

        CustomImageView productImage = dialog.getCustomImageView(R.id.thumbnail);
        productImage.setImage(cartItem.getImage());

        final NumberPicker quantity = dialog.getNumberPicker(R.id.quantity);
        quantity.setMinValue(1);
        quantity.setMaxValue(100);
        quantity.setValue(cartItem.getQuantity());

        dialog.setPositiveButton(R.string.button_accept, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                observer.onQuantityUpdated(cartItem, quantity.getValue());
            }
        });

        dialog.setNegativeButton(R.string.button_cancel, null);

        dialog.setNeutralButton(R.string.button_remove, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
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
}