package com.mauriciotogneri.shoppinglist.old.dao;

import com.mauriciotogneri.shoppinglist.old.model.CartItem;
import com.mauriciotogneri.shoppinglist.old.model.Product;
import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartItemDao
{
    public List<CartItem> getCartItems()
    {
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

        Collections.sort(notSelected, (lhs, rhs) -> lhs.getCategoryName().compareTo(rhs.getCategoryName()));

        Collections.sort(selected, (lhs, rhs) -> lhs.getName().compareTo(rhs.getName()));

        List<CartItem> result = new ArrayList<>(notSelected);

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