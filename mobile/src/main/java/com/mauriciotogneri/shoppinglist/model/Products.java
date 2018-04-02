package com.mauriciotogneri.shoppinglist.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Products
{
    private final Map<String, List<Product>> map;

    public Products()
    {
        this.map = new HashMap<>();
    }

    public void add(Product product)
    {
        if (map.containsKey(product.category))
        {
            map.get(product.category).add(product);
        }
        else
        {
            List<Product> list = new ArrayList<>();
            list.add(product);

            map.put(product.category, list);
        }
    }

    public Product[] byCategory(String category)
    {
        List<Product> products = map.get(category);

        return products.toArray(new Product[products.size()]);
    }

    public Set<Entry<String, List<Product>>> entries()
    {
        return map.entrySet();
    }

    public int size()
    {
        return map.size();
    }
}