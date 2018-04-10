package com.mauriciotogneri.shoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
        Category beverages = new Category(context.getString(R.string.category_beverage));
        Product beer = new Product(beverages.name(), context.getString(R.string.product_beer), "http://i.imgur.com/WmfEenJ.png", false, false);
        Product coffee = new Product(beverages.name(), context.getString(R.string.product_coffee), "http://i.imgur.com/aGiXxjf.png", false, false);
        Product iceTea = new Product(beverages.name(), context.getString(R.string.product_icedTea), "http://i.imgur.com/Spi8duE.png", false, false);
        Product soda = new Product(beverages.name(), context.getString(R.string.product_soda), "http://i.imgur.com/jybjFMf.png", false, false);
        Product water = new Product(beverages.name(), context.getString(R.string.product_water), "http://i.imgur.com/h34kKfv.png", false, false);

        Category breadAndGrain = new Category(context.getString(R.string.category_breadAndGrain));
        Product baguette = new Product(breadAndGrain.name(), context.getString(R.string.product_baguette), "http://i.imgur.com/6tf1nXp.png", false, false);
        Product cereals = new Product(breadAndGrain.name(), context.getString(R.string.product_cereals), "http://i.imgur.com/cjxBkMd.png", false, false);
        Product pasta = new Product(breadAndGrain.name(), context.getString(R.string.product_pasta), "http://i.imgur.com/VV4Z96m.png", false, false);
        Product rice = new Product(breadAndGrain.name(), context.getString(R.string.product_rice), "http://i.imgur.com/wOJ6XaT.png", false, false);
        Product risotto = new Product(breadAndGrain.name(), context.getString(R.string.product_risotto), "http://i.imgur.com/7dpAjjm.png", false, false);
        Product slicedBread = new Product(breadAndGrain.name(), context.getString(R.string.product_slicedBread), "http://i.imgur.com/Si3TyUt.png", false, false);

        Category condiments = new Category(context.getString(R.string.category_condimentsAndOthers));
        Product coffeeCream = new Product(condiments.name(), context.getString(R.string.product_coffeeCream), "http://i.imgur.com/uPn1oTB.png", false, false);
        Product corn = new Product(condiments.name(), context.getString(R.string.product_corn), "http://i.imgur.com/oZol6zV.png", false, false);
        Product oil = new Product(condiments.name(), context.getString(R.string.product_oil), "http://i.imgur.com/Us8YBF8.png", false, false);
        Product salt = new Product(condiments.name(), context.getString(R.string.product_salt), "http://i.imgur.com/NzU28LS.png", false, false);
        Product soup = new Product(condiments.name(), context.getString(R.string.product_soup), "http://i.imgur.com/lrUCxbM.png", false, false);
        Product sugar = new Product(condiments.name(), context.getString(R.string.product_sugar), "http://i.imgur.com/h1jCQQH.png", false, false);
        Product tomatoSauce = new Product(condiments.name(), context.getString(R.string.product_tomatoSauce), "http://i.imgur.com/WQYeED4.png", false, false);

        Category frozen = new Category(context.getString(R.string.category_frozen));
        Product fishSticks = new Product(frozen.name(), context.getString(R.string.product_fishSticks), "http://i.imgur.com/HpCxIrW.png", false, false);
        Product frenchFries = new Product(frozen.name(), context.getString(R.string.product_frenchFries), "http://i.imgur.com/KsWRC0d.png", false, false);
        Product iceCream = new Product(frozen.name(), context.getString(R.string.product_iceCream), "http://i.imgur.com/Jku9Mjy.png", false, false);
        Product lasagna = new Product(frozen.name(), context.getString(R.string.product_lasagna), "http://i.imgur.com/yizvszE.png", false, false);
        Product nuggets = new Product(frozen.name(), context.getString(R.string.product_nuggets), "http://i.imgur.com/uzuI4UK.png", false, false);
        Product pizza = new Product(frozen.name(), context.getString(R.string.product_pizza), "http://i.imgur.com/Kc7PiH9.png", false, false);

        Category fruitsAndVegetables = new Category(context.getString(R.string.category_fruitsAndVegetables));
        Product apples = new Product(fruitsAndVegetables.name(), context.getString(R.string.product_apples), "http://i.imgur.com/hkNdwlF.png", false, false);
        Product bananas = new Product(fruitsAndVegetables.name(), context.getString(R.string.product_bananas), "http://i.imgur.com/edf83Ow.png", false, false);
        Product carrots = new Product(fruitsAndVegetables.name(), context.getString(R.string.product_carrots), "http://i.imgur.com/rEUNdI0.png", false, false);
        Product lemons = new Product(fruitsAndVegetables.name(), context.getString(R.string.product_lemons), "http://i.imgur.com/i8wwGmB.png", false, false);
        Product lettuce = new Product(fruitsAndVegetables.name(), context.getString(R.string.product_lettuce), "http://i.imgur.com/NIguQT1.png", false, false);
        Product potatoes = new Product(fruitsAndVegetables.name(), context.getString(R.string.product_potatoes), "http://i.imgur.com/by3SaCG.png", false, false);

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
        categoryDao.insertAll(
                beverages,
                breadAndGrain,
                condiments,
                frozen,
                fruitsAndVegetables,
                household,
                meatAndFish,
                milkAndCheese);

        ProductDao productDao = productDao();
        productDao.insertAll(
                // Beverages
                beer, coffee, iceTea, soda, water,

                // Bread & Grain Products
                baguette, cereals, pasta, rice, risotto, slicedBread,

                // Condiments & Others
                coffeeCream, corn, oil, salt, soup, sugar, tomatoSauce,

                // Frozen
                fishSticks, frenchFries, iceCream, lasagna, nuggets, pizza,

                // Fruits & Vegetables
                apples, bananas, carrots, lemons, lettuce, potatoes,

                // Household
                airFreshener, bleach, bulb, cleaningSupplies, cottonSwabs, deodorant, dishwashingLiquid, garbageBags,
                hairRemover, kitchenRags, laundryDetergent, paperTowels, razor, shampoo, shavingFoam, showerGel, soap,
                sponge, spongeMetal, toiletPaper, toothbrush, toothpaste,

                // Meat & Fish
                chicken, fish, ham, meat, pork, salami, sardines, tuna,

                // Milk & Cheese
                butter, cheese, eggs, gratedCheese, milk, yogurt);
    }
}