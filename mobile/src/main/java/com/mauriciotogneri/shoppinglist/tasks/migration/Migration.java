package com.mauriciotogneri.shoppinglist.tasks.migration;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.database.AppDatabase;
import com.mauriciotogneri.shoppinglist.database.CategoryDao;
import com.mauriciotogneri.shoppinglist.database.ProductDao;
import com.mauriciotogneri.shoppinglist.old.CartItem;
import com.mauriciotogneri.shoppinglist.old.Category;
import com.mauriciotogneri.shoppinglist.old.Product;
import com.mauriciotogneri.shoppinglist.utils.ResourceUtils;
import com.orm.query.Select;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Migration extends AsyncTask<Void, Void, Void>
{
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final AppDatabase database;
    private final OnMigrationDone callback;
    private final ProgressDialog dialog;

    public Migration(Context context, OnMigrationDone callback)
    {
        this.context = context;
        this.database = AppDatabase.instance(context);
        this.callback = callback;
        this.dialog = new ProgressDialog(context);
        this.dialog.setMessage(context.getString(R.string.dialog_updatingDatabase));
    }

    @Override
    protected void onPreExecute()
    {
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        List<Category> categories = Select.from(Category.class).list();

        if (!categories.isEmpty())
        {
            CategoryDao categoryDao = database.categoryDao();
            ProductDao productDao = database.productDao();

            for (Category category : categories)
            {
                try
                {
                    categoryDao.insert(new com.mauriciotogneri.shoppinglist.model.Category(category.getName()));
                }
                catch (Exception e)
                {
                    // error creating category
                }
            }

            Set<Long> productsInCart = new HashSet<>();
            List<CartItem> cartItems = Select.from(CartItem.class).list();

            for (CartItem cartItem : cartItems)
            {
                try
                {
                    productDao.insert(product(
                            cartItem.getCategory(),
                            cartItem.getName(),
                            cartItem.getImage(),
                            true
                    ));

                    productsInCart.add(cartItem.productId());
                }
                catch (Exception e)
                {
                    // error creating product
                }
            }

            List<Product> products = Select.from(Product.class).list();

            for (Product product : products)
            {
                try
                {
                    if (!productsInCart.contains(product.getId()))
                    {
                        productDao.insert(product(
                                product.getCategory(),
                                product.getName(),
                                product.getImage(),
                                false
                        ));
                    }
                }
                catch (Exception e)
                {
                    // error creating product
                }
            }

            CartItem.deleteAll(CartItem.class);
            Product.deleteAll(Product.class);
            Category.deleteAll(Category.class);
        }
        else
        {
            database.initialize(context);
        }

        return null;
    }

    private com.mauriciotogneri.shoppinglist.model.Product product(String category, String name, byte[] image, Boolean inCart) throws Exception
    {
        File imageFile = ResourceUtils.createFile(context, image);

        return new com.mauriciotogneri.shoppinglist.model.Product(
                category,
                name,
                imageFile.getAbsolutePath(),
                inCart,
                false
        );
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        dialog.dismiss();

        callback.onMigrationDone();
    }

    public interface OnMigrationDone
    {
        void onMigrationDone();
    }
}