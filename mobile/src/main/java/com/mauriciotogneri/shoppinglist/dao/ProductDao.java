package com.mauriciotogneri.shoppinglist.dao;

import com.mauriciotogneri.shoppinglist.model.CartItem;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;
import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductDao
{
    public List<Product> getProducts(Category category)
    {
        List<Product> result = new ArrayList<>();
        List<Product> products = Select.from(Product.class).where("category = ?", new String[] {String.valueOf(category.getId())}).orderBy("name").list();

        for (Product product : products)
        {
            if (product.isValid() && (!isInCart(product)))
            {
                result.add(product);
            }
        }

        return result;
    }

    private boolean isInCart(Product product)
    {
        List<CartItem> cartItems = SugarRecord.find(CartItem.class, "product = ?", String.valueOf(product.getId()));

        return (!cartItems.isEmpty());
    }

    public Product byId(long id)
    {
        return SugarRecord.findById(Product.class, id);
    }

    public boolean exists(String name, Category category)
    {
        List<Product> products = SugarRecord.find(Product.class, "(name = ?) AND (category = ?)", name, String.valueOf(category.getId()));

        return (!products.isEmpty());
    }

    public boolean exists(Category category)
    {
        List<Product> products = SugarRecord.find(Product.class, "category = ?", String.valueOf(category.getId()));

        return (!products.isEmpty());
    }
}