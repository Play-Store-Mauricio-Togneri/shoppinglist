package com.mauriciotogneri.shoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mauriciotogneri.shoppinglist.model.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract ProductDao productDao();

    public static AppDatabase instance(Context context)
    {
        return Room.databaseBuilder(context, AppDatabase.class, "database").build();
    }

    public void initialize()
    {
        String beverages = "Beverages";
        Product beer = new Product(beverages, "Beer", "http://i.imgur.com/WmfEenJ.png", false, false);
        Product coffee = new Product(beverages, "Coffee", "http://i.imgur.com/aGiXxjf.png", false, false);
        Product iceTea = new Product(beverages, "Ice Tea", "http://i.imgur.com/Spi8duE.png", false, false);
        Product soda = new Product(beverages, "Soda", "http://i.imgur.com/jybjFMf.png", false, false);
        Product water = new Product(beverages, "Water", "http://i.imgur.com/h34kKfv.png", false, false);

        String breadAndGrain = "Bread & Grain Products";
        Product baguette = new Product(breadAndGrain, "Baguette", "http://i.imgur.com/6tf1nXp.png", false, false);
        Product cereals = new Product(breadAndGrain, "Cereals", "http://i.imgur.com/cjxBkMd.png", false, false);
        Product pasta = new Product(breadAndGrain, "Pasta", "http://i.imgur.com/VV4Z96m.png", false, false);
        Product rice = new Product(breadAndGrain, "Rice", "http://i.imgur.com/wOJ6XaT.png", false, false);
        Product risotto = new Product(breadAndGrain, "Risotto", "http://i.imgur.com/7dpAjjm.png", false, false);
        Product slicedBread = new Product(breadAndGrain, "Sliced bread", "http://i.imgur.com/Si3TyUt.png", false, false);

        String condiments = "Condiments & Others";
        Product coffeeCream = new Product(condiments, "Coffee cream", "http://i.imgur.com/uPn1oTB.png", false, false);
        Product corn = new Product(condiments, "Corn", "http://i.imgur.com/oZol6zV.png", false, false);
        Product oil = new Product(condiments, "Oil", "http://i.imgur.com/Us8YBF8.png", false, false);
        Product salt = new Product(condiments, "Salt", "http://i.imgur.com/NzU28LS.png", false, false);
        Product soup = new Product(condiments, "Soup", "http://i.imgur.com/lrUCxbM.png", false, false);
        Product sugar = new Product(condiments, "Sugar", "http://i.imgur.com/h1jCQQH.png", false, false);
        Product tomatoSauce = new Product(condiments, "Tomato sauce", "http://i.imgur.com/WQYeED4.png", false, false);

        String frozen = "Frozen";
        Product fishSticks = new Product(frozen, "Fish sticks", "http://i.imgur.com/HpCxIrW.png", false, false);
        Product frenchFries = new Product(frozen, "French fries", "http://i.imgur.com/KsWRC0d.png", false, false);
        Product iceCream = new Product(frozen, "Ice cream", "http://i.imgur.com/Jku9Mjy.png", false, false);
        Product lasagna = new Product(frozen, "Lasagna", "http://i.imgur.com/yizvszE.png", false, false);
        Product nuggets = new Product(frozen, "Nuggets", "http://i.imgur.com/uzuI4UK.png", false, false);
        Product pizza = new Product(frozen, "Pizza", "http://i.imgur.com/Kc7PiH9.png", false, false);

        String fruitsAndVegetables = "Fruits & Vegetables";
        Product apples = new Product(fruitsAndVegetables, "Apples", "http://i.imgur.com/hkNdwlF.png", false, false);
        Product bananas = new Product(fruitsAndVegetables, "Bananas", "http://i.imgur.com/edf83Ow.png", false, false);
        Product carrots = new Product(fruitsAndVegetables, "Carrots", "http://i.imgur.com/rEUNdI0.png", false, false);
        Product lemons = new Product(fruitsAndVegetables, "Lemons", "http://i.imgur.com/i8wwGmB.png", false, false);
        Product lettuce = new Product(fruitsAndVegetables, "Lettuce", "http://i.imgur.com/NIguQT1.png", false, false);
        Product tomatoes = new Product(fruitsAndVegetables, "Tomatoes", "http://i.imgur.com/We5LRdn.png", false, false);
        Product potatoes = new Product(fruitsAndVegetables, "Potatoes", "http://i.imgur.com/by3SaCG.png", false, false);

        String household = "Household";
        Product airFreshener = new Product(household, "Air freshener", "http://i.imgur.com/kLBRUMh.png", false, false);
        Product bleach = new Product(household, "Bleach", "http://i.imgur.com/CxdYGWK.png", false, false);
        Product bulb = new Product(household, "Bulb", "http://i.imgur.com/bp64U2i.png", false, false);
        Product cleaningSupplies = new Product(household, "Cleaning supplies", "http://i.imgur.com/dyhc3o4.png", false, false);
        Product cottonSwabs = new Product(household, "Cotton swabs", "http://i.imgur.com/tJ3oyEL.png", false, false);
        Product deodorant = new Product(household, "Deodorant", "http://i.imgur.com/14gYHs0.png", false, false);
        Product dishwashingLiquid = new Product(household, "Dishwashing liquid", "http://i.imgur.com/AcIzH1s.png", false, false);
        Product garbageBags = new Product(household, "Garbage bags", "http://i.imgur.com/ZYQK4Tm.png", false, false);
        Product hairRemover = new Product(household, "Hair remover", "http://i.imgur.com/bS6cyo1.png", false, false);
        Product kitchenRags = new Product(household, "Kitchen rags", "http://i.imgur.com/mvlB34z.png", false, false);
        Product laundryDetergent = new Product(household, "Laundry detergent", "http://i.imgur.com/6GQ3TgM.png", false, false);
        Product paperTowels = new Product(household, "Paper towels", "http://i.imgur.com/ZiYsOOA.png", false, false);
        Product razor = new Product(household, "Razor", "http://i.imgur.com/QYMgdEt.png", false, false);
        Product shampoo = new Product(household, "Shampoo", "http://i.imgur.com/1NWMm5T.png", false, false);
        Product shavingFoam = new Product(household, "Shaving foam", "http://i.imgur.com/n7w8V2b.png", false, false);
        Product showerGel = new Product(household, "Shower gel", "http://i.imgur.com/FZTkxdb.png", false, false);
        Product soap = new Product(household, "Soap", "http://i.imgur.com/mB6oVNU.png", false, false);
        Product sponge = new Product(household, "Sponge", "http://i.imgur.com/JijYcgb.png", false, false);
        Product spongeMetal = new Product(household, "Sponge metal", "http://i.imgur.com/BU63SGw.png", false, false);
        Product toiletPaper = new Product(household, "Toilet paper", "http://i.imgur.com/XFk0mUh.png", false, false);
        Product toothbrush = new Product(household, "Toothbrush", "http://i.imgur.com/oyAW8CW.png", false, false);
        Product toothpaste = new Product(household, "Toothpaste", "http://i.imgur.com/JUHf50H.png", false, false);

        String meatAndFish = "Meat & Fish";
        Product chicken = new Product(meatAndFish, "Chicken", "http://i.imgur.com/hYKIUgB.png", false, false);
        Product fish = new Product(meatAndFish, "Fish", "http://i.imgur.com/aSl5rtZ.png", false, false);
        Product ham = new Product(meatAndFish, "Ham", "http://i.imgur.com/TSGTD5X.png", false, false);
        Product meat = new Product(meatAndFish, "Meat", "http://i.imgur.com/NnvqpgY.png", false, false);
        Product pork = new Product(meatAndFish, "Pork", "http://i.imgur.com/GnjFKmN.png", false, false);
        Product salami = new Product(meatAndFish, "Salami", "http://i.imgur.com/Pddvg7H.png", false, false);
        Product sardines = new Product(meatAndFish, "Sardines", "http://i.imgur.com/wU9AVs3.png", false, false);
        Product tuna = new Product(meatAndFish, "Tuna", "http://i.imgur.com/M959WKh.png", false, false);

        String milkAndCheese = "Milk & Cheese";
        Product butter = new Product(milkAndCheese, "Butter", "http://i.imgur.com/GgQnfPI.png", false, false);
        Product cheese = new Product(milkAndCheese, "Cheese", "http://i.imgur.com/UvK6s35.png", false, false);
        Product eggs = new Product(milkAndCheese, "Eggs", "http://i.imgur.com/6gw4RrK.png", false, false);
        Product gratedCheese = new Product(milkAndCheese, "Grated cheese", "http://i.imgur.com/VgN12fA.png", false, false);
        Product milk = new Product(milkAndCheese, "Milk", "http://i.imgur.com/2nAlaR1.png", false, false);
        Product yogurt = new Product(milkAndCheese, "Yogurt", "http://i.imgur.com/yn4F7xG.png", false, false);

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
                apples, bananas, carrots, lemons, lettuce, tomatoes, potatoes,

                // Household
                airFreshener, bleach, bulb, cleaningSupplies, cottonSwabs, deodorant, dishwashingLiquid, garbageBags, hairRemover, kitchenRags, laundryDetergent, paperTowels, razor, shampoo, shavingFoam, showerGel, soap, sponge, spongeMetal, toiletPaper, toothbrush, toothpaste,

                // Meat & Fish
                chicken, fish, ham, meat, pork, salami, sardines, tuna,

                // Milk & Cheese
                butter, cheese, eggs, gratedCheese, milk, yogurt);
    }
}