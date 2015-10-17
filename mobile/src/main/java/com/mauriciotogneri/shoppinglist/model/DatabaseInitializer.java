package com.mauriciotogneri.shoppinglist.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mauriciotogneri.common.utils.ImageHelper;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.utils.ColorHelper;
import com.mauriciotogneri.shoppinglist.utils.Preferences;

public class DatabaseInitializer extends AsyncTask<Void, Void, Void>
{
    private final Context context;
    private final ProgressDialog progressDialog;
    private final Preferences preferences;

    public DatabaseInitializer(Context context)
    {
        this.context = context;

        this.preferences = Preferences.getInstance(context);

        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setMessage(context.getString(R.string.label_initializing_database));
        this.progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... urls)
    {
        // =========================== Fruits & Vegetables =========================== \\

        Category fruitsVegetables = new Category(context.getString(R.string.category_fruitsAndVegetables), ColorHelper.getColorAsHex(context, R.color.color_2));
        fruitsVegetables.save();

        Product apples = new Product(context.getString(R.string.product_apples), fruitsVegetables, ImageHelper.getImageBytesFromResource(context, R.drawable.product_apples));
        apples.save();

        Product bananas = new Product(context.getString(R.string.product_bananas), fruitsVegetables, ImageHelper.getImageBytesFromResource(context, R.drawable.product_bananas));
        bananas.save();

        Product carrots = new Product(context.getString(R.string.product_carrots), fruitsVegetables, ImageHelper.getImageBytesFromResource(context, R.drawable.product_carrots));
        carrots.save();

        Product lemons = new Product(context.getString(R.string.product_lemons), fruitsVegetables, ImageHelper.getImageBytesFromResource(context, R.drawable.product_lemons));
        lemons.save();

        Product lettuce = new Product(context.getString(R.string.product_lettuce), fruitsVegetables, ImageHelper.getImageBytesFromResource(context, R.drawable.product_lettuce));
        lettuce.save();

        Product pineapple = new Product(context.getString(R.string.product_pineapple), fruitsVegetables, ImageHelper.getImageBytesFromResource(context, R.drawable.product_pineapple));
        pineapple.save();

        Product potatoes = new Product(context.getString(R.string.product_potatoes), fruitsVegetables, ImageHelper.getImageBytesFromResource(context, R.drawable.product_potatoes));
        potatoes.save();

        // =========================== Bread & Grain Products =========================== \\

        Category breadGrainProducts = new Category(context.getString(R.string.category_breadAndGrain), ColorHelper.getColorAsHex(context, R.color.color_6));
        breadGrainProducts.save();

        Product baguette = new Product(context.getString(R.string.product_baguette), breadGrainProducts, ImageHelper.getImageBytesFromResource(context, R.drawable.product_baguette));
        baguette.save();

        Product cereals = new Product(context.getString(R.string.product_cereals), breadGrainProducts, ImageHelper.getImageBytesFromResource(context, R.drawable.product_cereals));
        cereals.save();

        Product pasta = new Product(context.getString(R.string.product_pasta), breadGrainProducts, ImageHelper.getImageBytesFromResource(context, R.drawable.product_pasta));
        pasta.save();

        Product rice = new Product(context.getString(R.string.product_rice), breadGrainProducts, ImageHelper.getImageBytesFromResource(context, R.drawable.product_rice));
        rice.save();

        Product risotto = new Product(context.getString(R.string.product_risotto), breadGrainProducts, ImageHelper.getImageBytesFromResource(context, R.drawable.product_risotto));
        risotto.save();

        Product slicedBread = new Product(context.getString(R.string.product_slicedBread), breadGrainProducts, ImageHelper.getImageBytesFromResource(context, R.drawable.product_sliced_bread));
        slicedBread.save();

        // =========================== Condiments & Others =========================== \\

        Category condimentsOthers = new Category(context.getString(R.string.category_condimentsAndOthers), ColorHelper.getColorAsHex(context, R.color.color_8));
        condimentsOthers.save();

        Product coffeeCream = new Product(context.getString(R.string.product_coffeeCream), condimentsOthers, ImageHelper.getImageBytesFromResource(context, R.drawable.product_coffee_cream));
        coffeeCream.save();

        Product corn = new Product(context.getString(R.string.product_corn), condimentsOthers, ImageHelper.getImageBytesFromResource(context, R.drawable.product_corn));
        corn.save();

        Product oil = new Product(context.getString(R.string.product_oil), condimentsOthers, ImageHelper.getImageBytesFromResource(context, R.drawable.product_oil));
        oil.save();

        Product salt = new Product(context.getString(R.string.product_salt), condimentsOthers, ImageHelper.getImageBytesFromResource(context, R.drawable.product_salt));
        salt.save();

        Product soup = new Product(context.getString(R.string.product_soup), condimentsOthers, ImageHelper.getImageBytesFromResource(context, R.drawable.product_soup));
        soup.save();

        Product sugar = new Product(context.getString(R.string.product_sugar), condimentsOthers, ImageHelper.getImageBytesFromResource(context, R.drawable.product_sugar));
        sugar.save();

        Product tomatoSauce = new Product(context.getString(R.string.product_tomatoSauce), condimentsOthers, ImageHelper.getImageBytesFromResource(context, R.drawable.product_tomato_sauce));
        tomatoSauce.save();

        // =========================== Milk & Cheese =========================== \\

        Category milkCheese = new Category(context.getString(R.string.category_milkAndCheese), ColorHelper.getColorAsHex(context, R.color.color_4));
        milkCheese.save();

        Product butter = new Product(context.getString(R.string.product_butter), milkCheese, ImageHelper.getImageBytesFromResource(context, R.drawable.product_butter));
        butter.save();

        Product cheese = new Product(context.getString(R.string.product_cheese), milkCheese, ImageHelper.getImageBytesFromResource(context, R.drawable.product_cheese));
        cheese.save();

        Product eggs = new Product(context.getString(R.string.product_eggs), milkCheese, ImageHelper.getImageBytesFromResource(context, R.drawable.product_eggs));
        eggs.save();

        Product gratedCheese = new Product(context.getString(R.string.product_gratedCheese), milkCheese, ImageHelper.getImageBytesFromResource(context, R.drawable.product_grated_cheese));
        gratedCheese.save();

        Product milk = new Product(context.getString(R.string.product_milk), milkCheese, ImageHelper.getImageBytesFromResource(context, R.drawable.product_milk));
        milk.save();

        Product yogurt = new Product(context.getString(R.string.product_yogurt), milkCheese, ImageHelper.getImageBytesFromResource(context, R.drawable.product_yogurt));
        yogurt.save();

        // =========================== Meat & Fish =========================== \\

        Category meatFish = new Category(context.getString(R.string.category_meatAndFish), ColorHelper.getColorAsHex(context, R.color.color_3));
        meatFish.save();

        Product chicken = new Product(context.getString(R.string.product_chicken), meatFish, ImageHelper.getImageBytesFromResource(context, R.drawable.product_chicken));
        chicken.save();

        Product fish = new Product(context.getString(R.string.product_fish), meatFish, ImageHelper.getImageBytesFromResource(context, R.drawable.product_fish));
        fish.save();

        Product ham = new Product(context.getString(R.string.product_ham), meatFish, ImageHelper.getImageBytesFromResource(context, R.drawable.product_ham));
        ham.save();

        Product meat = new Product(context.getString(R.string.product_meat), meatFish, ImageHelper.getImageBytesFromResource(context, R.drawable.product_meat));
        meat.save();

        Product pork = new Product(context.getString(R.string.product_pork), meatFish, ImageHelper.getImageBytesFromResource(context, R.drawable.product_pork));
        pork.save();

        Product salami = new Product(context.getString(R.string.product_salami), meatFish, ImageHelper.getImageBytesFromResource(context, R.drawable.product_salami));
        salami.save();

        Product sardines = new Product(context.getString(R.string.product_sardines), meatFish, ImageHelper.getImageBytesFromResource(context, R.drawable.product_sardines));
        sardines.save();

        Product tuna = new Product(context.getString(R.string.product_tuna), meatFish, ImageHelper.getImageBytesFromResource(context, R.drawable.product_tuna));
        tuna.save();

        // =========================== Frozen =========================== \\

        Category frozen = new Category(context.getString(R.string.category_frozen), ColorHelper.getColorAsHex(context, R.color.color_1));
        frozen.save();

        Product fishSticks = new Product(context.getString(R.string.product_fishSticks), frozen, ImageHelper.getImageBytesFromResource(context, R.drawable.product_fish_sticks));
        fishSticks.save();

        Product frenchFries = new Product(context.getString(R.string.product_frenchFries), frozen, ImageHelper.getImageBytesFromResource(context, R.drawable.product_french_fries));
        frenchFries.save();

        Product iceCream = new Product(context.getString(R.string.product_iceCream), frozen, ImageHelper.getImageBytesFromResource(context, R.drawable.product_ice_cream));
        iceCream.save();

        Product lasagna = new Product(context.getString(R.string.product_lasagna), frozen, ImageHelper.getImageBytesFromResource(context, R.drawable.product_lasagna));
        lasagna.save();

        Product nuggets = new Product(context.getString(R.string.product_nuggets), frozen, ImageHelper.getImageBytesFromResource(context, R.drawable.product_nuggets));
        nuggets.save();

        Product pizza = new Product(context.getString(R.string.product_pizza), frozen, ImageHelper.getImageBytesFromResource(context, R.drawable.product_pizza));
        pizza.save();

        // =========================== Beverage =========================== \\

        Category beverage = new Category(context.getString(R.string.category_beverage), ColorHelper.getColorAsHex(context, R.color.color_5));
        beverage.save();

        Product beer = new Product(context.getString(R.string.product_beer), beverage, ImageHelper.getImageBytesFromResource(context, R.drawable.product_beer));
        beer.save();

        Product coffee = new Product(context.getString(R.string.product_coffee), beverage, ImageHelper.getImageBytesFromResource(context, R.drawable.product_coffee));
        coffee.save();

        Product icedTea = new Product(context.getString(R.string.product_icedTea), beverage, ImageHelper.getImageBytesFromResource(context, R.drawable.product_ice_tea));
        icedTea.save();

        Product soda = new Product(context.getString(R.string.product_soda), beverage, ImageHelper.getImageBytesFromResource(context, R.drawable.product_soda));
        soda.save();

        Product water = new Product(context.getString(R.string.product_water), beverage, ImageHelper.getImageBytesFromResource(context, R.drawable.product_water));
        water.save();

        // =========================== Household =========================== \\

        Category household = new Category(context.getString(R.string.category_household), ColorHelper.getColorAsHex(context, R.color.color_7));
        household.save();

        Product airFreshener = new Product(context.getString(R.string.product_airFreshener), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_air_freshener));
        airFreshener.save();

        Product bleach = new Product(context.getString(R.string.product_bleach), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_bleach));
        bleach.save();

        Product bulb = new Product(context.getString(R.string.product_bulb), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_bulb));
        bulb.save();

        Product cleaningSupplies = new Product(context.getString(R.string.product_cleaningSupplies), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_cleaning_supplies));
        cleaningSupplies.save();

        Product deodorant = new Product(context.getString(R.string.product_deodorant), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_deodorant));
        deodorant.save();

        Product dishwashingLiquid = new Product(context.getString(R.string.product_dishwashingLiquid), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_dishwashing_liquid));
        dishwashingLiquid.save();

        Product hairRemover = new Product(context.getString(R.string.product_hairRemover), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_hair_remover));
        hairRemover.save();

        Product kitchenRags = new Product(context.getString(R.string.product_kitchenRags), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_kitchen_rags));
        kitchenRags.save();

        Product garbageBags = new Product(context.getString(R.string.product_garbageBags), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_garbage_bags));
        garbageBags.save();

        Product laundryDetergent = new Product(context.getString(R.string.product_laundryDetergent), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_laundry_detergent));
        laundryDetergent.save();

        Product paperTowels = new Product(context.getString(R.string.product_paperTowels), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_paper_towels));
        paperTowels.save();

        Product razor = new Product(context.getString(R.string.product_razor), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_razor));
        razor.save();

        Product shampoo = new Product(context.getString(R.string.product_shampoo), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_shampoo));
        shampoo.save();

        Product shavingFoam = new Product(context.getString(R.string.product_shavingFoam), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_shaving_foam));
        shavingFoam.save();

        Product showerGel = new Product(context.getString(R.string.product_showerGel), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_shower_gel));
        showerGel.save();

        Product soap = new Product(context.getString(R.string.product_soap), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_soap));
        soap.save();

        Product sponge = new Product(context.getString(R.string.product_sponge), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_sponge_normal));
        sponge.save();

        Product spongeMetal = new Product(context.getString(R.string.product_spongeMetal), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_sponge_metal));
        spongeMetal.save();

        Product swabs = new Product(context.getString(R.string.product_swabs), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_swabs));
        swabs.save();

        Product toiletPaper = new Product(context.getString(R.string.product_toiletPaper), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_toilet_paper));
        toiletPaper.save();

        Product toothbrush = new Product(context.getString(R.string.product_toothbrush), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_toothbrush));
        toothbrush.save();

        Product toothpaste = new Product(context.getString(R.string.product_toothpaste), household, ImageHelper.getImageBytesFromResource(context, R.drawable.product_toothpaste));
        toothpaste.save();

        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);

        preferences.setFirstLaunch(false);

        progressDialog.dismiss();
    }
}