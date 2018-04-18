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
        // TODO: REPLACE THE HTTPS FOR HTTP
        Category beverages = new Category(context.getString(R.string.category_beverage));
        Product beer = product(context, R.string.product_beer, beverages, "http://i.imgur.com/OAYaqkM.png");
        Product coffee = product(context, R.string.product_coffee, beverages, "http://i.imgur.com/SCW2fcK.png");
        Product soda = product(context, R.string.product_soda, beverages, "http://i.imgur.com/FagEbsU.png");
        Product water = product(context, R.string.product_water, beverages, "http://i.imgur.com/XWVwKDK.png");

        Category breadAndGrain = new Category(context.getString(R.string.category_breadAndGrain));
        Product baguette = product(context, R.string.product_baguette, breadAndGrain, "http://i.imgur.com/hkI7tAI.png");
        Product cereals = product(context, R.string.product_cereals, breadAndGrain, "http://i.imgur.com/cY6UouB.png");
        Product pasta = product(context, R.string.product_pasta, breadAndGrain, "https://i.imgur.com/yzrIBX1.png");
        Product rice = product(context, R.string.product_rice, breadAndGrain, "http://i.imgur.com/psXXfuG.png");

        Category condiments = new Category(context.getString(R.string.category_condimentsAndOthers));
        Product oil = product(context, R.string.product_oil, condiments, "http://i.imgur.com/r2ZecGS.png");
        Product pepper = product(context, R.string.product_pepper, condiments, "https://i.imgur.com/SjEIvSI.png");
        Product salt = product(context, R.string.product_salt, condiments, "http://i.imgur.com/o14W3sV.png");
        Product tomatoSauce = product(context, R.string.product_tomatoSauce, condiments, "http://i.imgur.com/GGnudqb.png");

        Category frozen = new Category(context.getString(R.string.category_frozen));
        Product frenchFries = product(context, R.string.product_frenchFries, frozen, "http://i.imgur.com/UEeHBCY.png");
        Product iceCream = product(context, R.string.product_iceCream, frozen, "http://i.imgur.com/ewXqBfF.png");
        Product lasagna = product(context, R.string.product_lasagna, frozen, "http://i.imgur.com/dLp0saj.png");
        Product pizza = product(context, R.string.product_pizza, frozen, "http://i.imgur.com/Zist1Zr.png");

        Category fruitsAndVegetables = new Category(context.getString(R.string.category_fruitsAndVegetables));
        Product apples = product(context, R.string.product_apples, fruitsAndVegetables, "https://i.imgur.com/tQRbEZh.png");
        Product bananas = product(context, R.string.product_bananas, fruitsAndVegetables, "https://i.imgur.com/9iCHh7G.png");
        Product carrots = product(context, R.string.product_carrots, fruitsAndVegetables, "https://i.imgur.com/h8FxUEd.png");
        Product potatoes = product(context, R.string.product_potatoes, fruitsAndVegetables, "https://i.imgur.com/f2OsMoE.png");

        // -------------------------------------------------------------------------------------------

        Category household = new Category(context.getString(R.string.category_household));
        Product airFreshener = new Product(household.name(), context.getString(R.string.product_airFreshener), "http://i.imgur.com/kLBRUMh.png", false, false);
        Product bleach = new Product(household.name(), context.getString(R.string.product_bleach), "http://i.imgur.com/CxdYGWK.png", false, false);
        Product bulb = new Product(household.name(), context.getString(R.string.product_bulb), "http://i.imgur.com/bp64U2i.png", false, false);
        Product cleaningSupplies = new Product(household.name(), context.getString(R.string.product_cleaningSupplies), "http://i.imgur.com/dyhc3o4.png", false, false);
        Product cottonSwabs = new Product(household.name(), context.getString(R.string.product_cottonSwabs), "http://i.imgur.com/tJ3oyEL.png", false, false);
        Product deodorant = new Product(household.name(), context.getString(R.string.product_deodorant), "http://i.imgur.com/14gYHs0.png", false, false);
        Product dishwashingLiquid = new Product(household.name(), context.getString(R.string.product_dishwashingLiquid), "http://i.imgur.com/AcIzH1s.png", false, false);
        Product garbageBags = new Product(household.name(), context.getString(R.string.product_garbageBags), "http://i.imgur.com/ZYQK4Tm.png", false, false);
        Product hairRemover = new Product(household.name(), context.getString(R.string.product_hairRemover), "http://i.imgur.com/bS6cyo1.png", false, false);
        Product kitchenRags = new Product(household.name(), context.getString(R.string.product_kitchenRags), "http://i.imgur.com/mvlB34z.png", false, false);
        Product laundryDetergent = new Product(household.name(), context.getString(R.string.product_laundryDetergent), "http://i.imgur.com/6GQ3TgM.png", false, false);
        Product paperTowels = new Product(household.name(), context.getString(R.string.product_paperTowels), "http://i.imgur.com/ZiYsOOA.png", false, false);
        Product razor = new Product(household.name(), context.getString(R.string.product_razor), "http://i.imgur.com/QYMgdEt.png", false, false);
        Product shampoo = new Product(household.name(), context.getString(R.string.product_shampoo), "http://i.imgur.com/1NWMm5T.png", false, false);
        Product shavingFoam = new Product(household.name(), context.getString(R.string.product_shavingFoam), "http://i.imgur.com/n7w8V2b.png", false, false);
        Product showerGel = new Product(household.name(), context.getString(R.string.product_showerGel), "http://i.imgur.com/FZTkxdb.png", false, false);
        Product soap = new Product(household.name(), context.getString(R.string.product_soap), "http://i.imgur.com/mB6oVNU.png", false, false);
        Product sponge = new Product(household.name(), context.getString(R.string.product_sponge), "http://i.imgur.com/JijYcgb.png", false, false);
        Product spongeMetal = new Product(household.name(), context.getString(R.string.product_spongeMetal), "http://i.imgur.com/BU63SGw.png", false, false);
        Product toiletPaper = new Product(household.name(), context.getString(R.string.product_toiletPaper), "http://i.imgur.com/XFk0mUh.png", false, false);
        Product toothbrush = new Product(household.name(), context.getString(R.string.product_toothbrush), "http://i.imgur.com/oyAW8CW.png", false, false);
        Product toothpaste = new Product(household.name(), context.getString(R.string.product_toothpaste), "http://i.imgur.com/JUHf50H.png", false, false);

        Category meatAndFish = new Category(context.getString(R.string.category_meatAndFish));
        Product chicken = new Product(meatAndFish.name(), context.getString(R.string.product_chicken), "http://i.imgur.com/hYKIUgB.png", false, false);
        Product fish = new Product(meatAndFish.name(), context.getString(R.string.product_fish), "http://i.imgur.com/aSl5rtZ.png", false, false);
        Product ham = new Product(meatAndFish.name(), context.getString(R.string.product_ham), "http://i.imgur.com/TSGTD5X.png", false, false);
        Product meat = new Product(meatAndFish.name(), context.getString(R.string.product_meat), "http://i.imgur.com/NnvqpgY.png", false, false);
        Product pork = new Product(meatAndFish.name(), context.getString(R.string.product_pork), "http://i.imgur.com/GnjFKmN.png", false, false);
        Product salami = new Product(meatAndFish.name(), context.getString(R.string.product_salami), "http://i.imgur.com/Pddvg7H.png", false, false);
        Product sardines = new Product(meatAndFish.name(), context.getString(R.string.product_sardines), "http://i.imgur.com/wU9AVs3.png", false, false);
        Product tuna = new Product(meatAndFish.name(), context.getString(R.string.product_tuna), "http://i.imgur.com/M959WKh.png", false, false);

        Category milkAndCheese = new Category(context.getString(R.string.category_milkAndCheese));
        Product butter = new Product(milkAndCheese.name(), context.getString(R.string.product_butter), "http://i.imgur.com/GgQnfPI.png", false, false);
        Product cheese = new Product(milkAndCheese.name(), context.getString(R.string.product_cheese), "http://i.imgur.com/UvK6s35.png", false, false);
        Product eggs = new Product(milkAndCheese.name(), context.getString(R.string.product_eggs), "http://i.imgur.com/6gw4RrK.png", false, false);
        Product gratedCheese = new Product(milkAndCheese.name(), context.getString(R.string.product_gratedCheese), "http://i.imgur.com/VgN12fA.png", false, false);
        Product milk = new Product(milkAndCheese.name(), context.getString(R.string.product_milk), "http://i.imgur.com/2nAlaR1.png", false, false);
        Product yogurt = new Product(milkAndCheese.name(), context.getString(R.string.product_yogurt), "http://i.imgur.com/yn4F7xG.png", false, false);

        CategoryDao categoryDao = categoryDao();
        categoryDao.insert(
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
                airFreshener, bleach, bulb, cleaningSupplies, cottonSwabs, deodorant, dishwashingLiquid, garbageBags,
                hairRemover, kitchenRags, laundryDetergent, paperTowels, razor, shampoo, shavingFoam, showerGel, soap,
                sponge, spongeMetal, toiletPaper, toothbrush, toothpaste,

                // Meat & Fish
                chicken, fish, ham, meat, pork, salami, sardines, tuna,

                // Milk & Cheese
                butter, cheese, eggs, gratedCheese, milk, yogurt);
    }

    private Product product(Context context, @StringRes int resId, Category category, String imageUrl)
    {
        return new Product(category.name(), context.getString(resId), imageUrl, false, false);
    }
}