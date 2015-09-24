package com.mauriciotogneri.shoppinglist.views.productslist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ListProductAdapter;
import com.mauriciotogneri.shoppinglist.adapters.MenuItemAdapter;
import com.mauriciotogneri.shoppinglist.adapters.MenuItemAdapter.Option;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogConfirmation;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogError;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogMenu;
import com.mauriciotogneri.shoppinglist.views.productslist.ProductsListView.UiContainer;

import java.util.ArrayList;
import java.util.List;

public class ProductsListView extends BaseView<UiContainer> implements ProductsListViewInterface<UiContainer>
{
    private ListProductAdapter listProductAdapter;

    @Override
    public void initialize(final Context context, final ProductsListViewObserver observer)
    {
        listProductAdapter = new ListProductAdapter(context);

        ui.list.setAdapter(listProductAdapter);

        ui.list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Product product = (Product) parent.getItemAtPosition(position);
                observer.onAddProduct(product);
            }
        });

        ui.list.setOnItemLongClickListener(new OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                Product product = (Product) parent.getItemAtPosition(position);
                displayProductOptions(context, product, observer);

                return true;
            }
        });
    }

    @SuppressLint("InflateParams")
    private void displayProductOptions(final Context context, final Product product, final ProductsListViewObserver observer)
    {
        final int EDIT_PRODUCT = 0;
        final int REMOVE_PRODUCT = 1;

        List<Option> optionsList = new ArrayList<>();
        optionsList.add(new Option(context.getString(R.string.icon_edit), context.getString(R.string.button_edit)));
        optionsList.add(new Option(context.getString(R.string.icon_remove), context.getString(R.string.button_remove)));

        DialogMenu dialog = new DialogMenu(context, product.getName());

        ListAdapter menuItemAdapter = new MenuItemAdapter(context, optionsList);
        dialog.setAdapter(menuItemAdapter, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int index)
            {
                switch (index)
                {
                    case EDIT_PRODUCT:
                        observer.onEditProduct(product);
                        break;

                    case REMOVE_PRODUCT:
                        removeProduct(context, product, observer);
                        break;
                }
            }
        });

        dialog.display();
    }

    @SuppressLint("InflateParams")
    private void removeProduct(Context context, final Product product, final ProductsListViewObserver observer)
    {
        DialogConfirmation dialog = new DialogConfirmation(context, product.getName());
        dialog.initialize(R.string.confirmation_remove_product, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                observer.onRemoveProduct(product);
            }
        });
        dialog.display();
    }

    @Override
    public void showError()
    {
        DialogError dialog = new DialogError(getContext());
        dialog.initialize(R.string.error_product_in_use);
        dialog.display();
    }

    @Override
    public void fillList(List<Product> list)
    {
        listProductAdapter.update(list);

        if (listProductAdapter.getCount() > 0)
        {
            ui.list.setVisibility(View.VISIBLE);
            ui.list.setSelection(0);
            ui.emptyLabel.setVisibility(View.GONE);
        }
        else
        {
            ui.list.setVisibility(View.GONE);
            ui.emptyLabel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getViewId()
    {
        return R.layout.view_products_list;
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

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.list = (ListView) findViewById(R.id.list);
            this.emptyLabel = (TextView) findViewById(R.id.empty_label);
        }
    }
}