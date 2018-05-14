package com.mauriciotogneri.shoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.StringRes;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.model.Product;

@Database(entities = {Product.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public static AppDatabase instance(Context context)
    {
        return Room.databaseBuilder(context, AppDatabase.class, "database").build();
    }

    public void initialize(Context context)
    {
        Category bathroom = new Category(context.getString(R.string.category_bathroom));
        Product shampoo = product(context, R.string.product_shampoo, bathroom, "http://i.imgur.com/BM0GFUC.png");
        Product toiletPaper = product(context, R.string.product_toiletPaper, bathroom, "http://i.imgur.com/LDI12E4.png");
        Product toothbrush = product(context, R.string.product_toothbrush, bathroom, "http://i.imgur.com/Lgz5fTy.png");
        Product toothpaste = product(context, R.string.product_toothpaste, bathroom, "http://i.imgur.com/glyrlhS.png");

        Category beverages = new Category(context.getString(R.string.category_beverage));
        Product beer = product(context, R.string.product_beer, beverages, "http://i.imgur.com/OAYaqkM.png");
        Product coffee = product(context, R.string.product_coffee, beverages, "http://i.imgur.com/SCW2fcK.png");
        Product soda = product(context, R.string.product_soda, beverages, "http://i.imgur.com/FagEbsU.png");
        Product water = product(context, R.string.product_water, beverages, "http://i.imgur.com/XWVwKDK.png");

        Category breadAndGrain = new Category(context.getString(R.string.category_breadAndGrain));
        Product baguette = product(context, R.string.product_baguette, breadAndGrain, "http://i.imgur.com/hkI7tAI.png");
        Product cereals = product(context, R.string.product_cereals, breadAndGrain, "http://i.imgur.com/cY6UouB.png");
        Product pasta = product(context, R.string.product_pasta, breadAndGrain, "http://i.imgur.com/yzrIBX1.png");
        Product rice = product(context, R.string.product_rice, breadAndGrain, "http://i.imgur.com/psXXfuG.png");

        Category condiments = new Category(context.getString(R.string.category_condimentsAndOthers));
        Product oil = product(context, R.string.product_oil, condiments, "http://i.imgur.com/r2ZecGS.png");
        Product pepper = product(context, R.string.product_pepper, condiments, "http://i.imgur.com/SjEIvSI.png");
        Product salt = product(context, R.string.product_salt, condiments, "http://i.imgur.com/o14W3sV.png");
        Product tomatoSauce = product(context, R.string.product_tomatoSauce, condiments, "http://i.imgur.com/GGnudqb.png");

        Category frozen = new Category(context.getString(R.string.category_frozen));
        Product frenchFries = product(context, R.string.product_frenchFries, frozen, "http://i.imgur.com/UEeHBCY.png");
        Product iceCream = product(context, R.string.product_iceCream, frozen, "http://i.imgur.com/ewXqBfF.png");
        Product lasagna = product(context, R.string.product_lasagna, frozen, "http://i.imgur.com/dLp0saj.png");
        Product pizza = product(context, R.string.product_pizza, frozen, "http://i.imgur.com/Zist1Zr.png");

        Category fruitsAndVegetables = new Category(context.getString(R.string.category_fruitsAndVegetables));
        Product apples = product(context, R.string.product_apples, fruitsAndVegetables, "http://i.imgur.com/tQRbEZh.png");
        Product bananas = product(context, R.string.product_bananas, fruitsAndVegetables, "http://i.imgur.com/9iCHh7G.png");
        Product carrots = product(context, R.string.product_carrots, fruitsAndVegetables, "http://i.imgur.com/h8FxUEd.png");
        Product potatoes = product(context, R.string.product_potatoes, fruitsAndVegetables, "http://i.imgur.com/f2OsMoE.png");

        Category household = new Category(context.getString(R.string.category_household));
        Product cleaningSupplies = product(context, R.string.product_cleaningSupplies, household, "http://i.imgur.com/mU7N85R.png");
        Product dishwashingLiquid = product(context, R.string.product_dishwashingLiquid, household, "http://i.imgur.com/gMZ40BT.png");
        Product garbageBags = product(context, R.string.product_garbageBags, household, "http://i.imgur.com/DekDs24.png");
        Product laundryDetergent = product(context, R.string.product_laundryDetergent, household, "http://i.imgur.com/oi5EFNa.png");

        Category meatAndFish = new Category(context.getString(R.string.category_meatAndFish));
        Product chicken = product(context, R.string.product_chicken, meatAndFish, "http://i.imgur.com/Cjsu3Sl.png");
        Product fish = product(context, R.string.product_fish, meatAndFish, "http://i.imgur.com/Wzumh0P.png");
        Product meat = product(context, R.string.product_meat, meatAndFish, "http://i.imgur.com/4ru5VVy.png");
        Product tuna = product(context, R.string.product_tuna, meatAndFish, "http://i.imgur.com/MgrEyWa.png");

        Category milkAndCheese = new Category(context.getString(R.string.category_milkAndCheese));
        Product cheese = product(context, R.string.product_cheese, milkAndCheese, "http://i.imgur.com/ebP7mp2.png");
        Product eggs = product(context, R.string.product_eggs, milkAndCheese, "http://i.imgur.com/aIXuDqG.png");
        Product milk = product(context, R.string.product_milk, milkAndCheese, "http://i.imgur.com/ERuiwHw.png");
        Product yogurt = product(context, R.string.product_yogurt, milkAndCheese, "http://i.imgur.com/mhxVBOA.png");

        CategoryDao categoryDao = categoryDao();
        categoryDao.insert(
                bathroom,
                beverages,
                breadAndGrain,
                condiments,
                frozen,
                fruitsAndVegetables,
                household,
                meatAndFish,
                milkAndCheese);

        ProductDao productDao = productDao();
        productDao.insert(
                // Bathroom
                shampoo, toiletPaper, toothbrush, toothpaste,

                // Beverages
                beer, coffee, soda, water,

                // Bread & Grain Products
                baguette, cereals, pasta, rice,

                // Condiments & Others
                oil, pepper, salt, tomatoSauce,

                // Frozen
                frenchFries, iceCream, lasagna, pizza,

                // Fruits & Vegetables
                apples, bananas, carrots, potatoes,

                // Household
                cleaningSupplies, dishwashingLiquid, garbageBags, laundryDetergent,

                // Meat & Fish
                chicken, fish, meat, tuna,

                // Milk & Cheese
                cheese, eggs, milk, yogurt);
    }

    private Product product(Context context, @StringRes int resId, Category category, String imageUrl)
    {
        return new Product(category.name(), context.getString(resId), imageUrl, false, false);
    }
}