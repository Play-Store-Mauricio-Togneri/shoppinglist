package com.mauriciotogneri.shoppinglist.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<String> categories()
    {
        List<String> categories = new ArrayList<>(map.keySet());
        Collections.sort(categories);

        return categories;
    }

    public int size()
    {
        return map.size();
    }
}