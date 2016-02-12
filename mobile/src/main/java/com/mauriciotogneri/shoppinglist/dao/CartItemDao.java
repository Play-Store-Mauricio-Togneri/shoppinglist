package com.mauriciotogneri.shoppinglist.dao;

import com.mauriciotogneri.shoppinglist.model.CartItem;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CartItemDao
{
    public List<CartItem> getCartItems()
    {
        List<CartItem> result = new ArrayList<>();
        List<CartItem> cartItems = Select.from(CartItem.class).list();

        List<CartItem> notSelected = new ArrayList<>();
        List<CartItem> selected = new ArrayList<>();

        for (CartItem cartItem : cartItems)
        {
            if (cartItem.isValid())
            {
                if (cartItem.isSelected())
                {
                    selected.add(cartItem);
                }
                else
                {
                    notSelected.add(cartItem);
                }
            }
        }

        Collections.sort(notSelected, new Comparator<CartItem>()
        {
            @Override
            public int compare(CartItem lhs, CartItem rhs)
            {
                return lhs.getCategoryName().compareTo(rhs.getCategoryName());
            }
        });

        Collections.sort(selected, new Comparator<CartItem>()
        {
            @Override
            public int compare(CartItem lhs, CartItem rhs)
            {
                return lhs.getName().compareTo(rhs.getName());
            }
        });

        result.addAll(notSelected);

        if (!selected.isEmpty())
        {
            result.addAll(selected);
        }

        return result;
    }

    public boolean exists(Product product)
    {
        CartItem cartItem = Select.from(CartItem.class).where("product = ?", new String[] {String.valueOf(product.getId())}).first();

        return (cartItem != null);
    }

    public void mark(long id, boolean isSelected)
    {
        CartItem cartItem = SugarRecord.findById(CartItem.class, id);

        if (cartItem != null)
        {
            cartItem.setSelected(isSelected);
            cartItem.save();
        }
    }
}