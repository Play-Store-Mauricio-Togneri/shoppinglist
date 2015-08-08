package com.mauriciotogneri.shoppinglist.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.mauriciotogneri.shoppinglist.R;

import java.io.ByteArrayOutputStream;

public class DatabaseInitializer extends AsyncTask<Void, Void, Void>
{
    private final Context context;
    private final ProgressDialog progressDialog;

    public DatabaseInitializer(Context context)
    {
        this.context = context;

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

        Category fruitsVegetables = new Category(context.getString(R.string.category_fruitsAndVegetables), Category.COLOR_3);
        fruitsVegetables.save();

        Product apples = new Product(context.getString(R.string.product_apples), fruitsVegetables, getImageBytes(this.context, R.drawable.product_apples));
        apples.save();

        Product bananas = new Product(context.getString(R.string.product_bananas), fruitsVegetables, getImageBytes(this.context, R.drawable.product_bananas));
        bananas.save();

        Product carrots = new Product(context.getString(R.string.product_carrots), fruitsVegetables, getImageBytes(this.context, R.drawable.product_carrots));
        carrots.save();

        Product lemons = new Product(context.getString(R.string.product_lemons), fruitsVegetables, getImageBytes(this.context, R.drawable.product_lemons));
        lemons.save();

        Product lettuce = new Product(context.getString(R.string.product_lettuce), fruitsVegetables, getImageBytes(this.context, R.drawable.product_lettuce));
        lettuce.save();

        Product pineapple = new Product(context.getString(R.string.product_pineapple), fruitsVegetables, getImageBytes(this.context, R.drawable.product_pineapple));
        pineapple.save();

        Product potatoes = new Product(context.getString(R.string.product_potatoes), fruitsVegetables, getImageBytes(this.context, R.drawable.product_potatoes));
        potatoes.save();

        // =========================== Bread & Grain Products =========================== \\

        Category breadGrainProducts = new Category(context.getString(R.string.category_breadAndGrain), Category.COLOR_6);
        breadGrainProducts.save();

        Product baguette = new Product(context.getString(R.string.product_baguette), breadGrainProducts, getImageBytes(this.context, R.drawable.product_baguette));
        baguette.save();

        Product cereals = new Product(context.getString(R.string.product_cereals), breadGrainProducts, getImageBytes(this.context, R.drawable.product_cereals));
        cereals.save();

        Product pasta = new Product(context.getString(R.string.product_pasta), breadGrainProducts, getImageBytes(this.context, R.drawable.product_pasta));
        pasta.save();

        Product rice = new Product(context.getString(R.string.product_rice), breadGrainProducts, getImageBytes(this.context, R.drawable.product_rice));
        rice.save();

        Product slicedBread = new Product(context.getString(R.string.product_slicedBread), breadGrainProducts, getImageBytes(this.context, R.drawable.product_sliced_bread));
        slicedBread.save();

        // =========================== Condiments & Others =========================== \\

        Category condimentsOthers = new Category(context.getString(R.string.category_condimentsAndOthers), Category.COLOR_1);
        condimentsOthers.save();

        Product coffeeCream = new Product(context.getString(R.string.product_coffeeCream), condimentsOthers, getImageBytes(this.context, R.drawable.product_coffee_cream));
        coffeeCream.save();

        Product corn = new Product(context.getString(R.string.product_corn), condimentsOthers, getImageBytes(this.context, R.drawable.product_corn));
        corn.save();

        Product risotto = new Product(context.getString(R.string.product_risotto), condimentsOthers, getImageBytes(this.context, R.drawable.product_risotto));
        risotto.save();

        Product salt = new Product(context.getString(R.string.product_salt), condimentsOthers, getImageBytes(this.context, R.drawable.product_salt));
        salt.save();

        Product soup = new Product(context.getString(R.string.product_soup), condimentsOthers, getImageBytes(this.context, R.drawable.product_soup));
        soup.save();

        Product sugar = new Product(context.getString(R.string.product_sugar), condimentsOthers, getImageBytes(this.context, R.drawable.product_sugar));
        sugar.save();

        Product oil = new Product(context.getString(R.string.product_oil), condimentsOthers, getImageBytes(this.context, R.drawable.product_oil));
        oil.save();

        Product tomatoSauce = new Product(context.getString(R.string.product_tomatoSauce), condimentsOthers, getImageBytes(this.context, R.drawable.product_tomato_sauce));
        tomatoSauce.save();

        // =========================== Milk & Cheese =========================== \\

        Category milkCheese = new Category(context.getString(R.string.category_milkAndCheese), Category.COLOR_7);
        milkCheese.save();

        Product butter = new Product(context.getString(R.string.product_butter), milkCheese, getImageBytes(this.context, R.drawable.product_butter));
        butter.save();

        Product cheese = new Product(context.getString(R.string.product_cheese), milkCheese, getImageBytes(this.context, R.drawable.product_cheese));
        cheese.save();

        Product eggs = new Product(context.getString(R.string.product_eggs), milkCheese, getImageBytes(this.context, R.drawable.product_eggs));
        eggs.save();

        Product gratedCheese = new Product(context.getString(R.string.product_gratedCheese), milkCheese, getImageBytes(this.context, R.drawable.product_grated_cheese));
        gratedCheese.save();

        Product milk = new Product(context.getString(R.string.product_milk), milkCheese, getImageBytes(this.context, R.drawable.product_milk));
        milk.save();

        Product yogurt = new Product(context.getString(R.string.product_yogurt), milkCheese, getImageBytes(this.context, R.drawable.product_yogurt));
        yogurt.save();

        // =========================== Meat & Fish =========================== \\

        Category meatFish = new Category(context.getString(R.string.category_meatAndFish), Category.COLOR_4);
        meatFish.save();

        Product chicken = new Product(context.getString(R.string.product_chicken), meatFish, getImageBytes(this.context, R.drawable.product_chicken));
        chicken.save();

        Product fish = new Product(context.getString(R.string.product_fish), meatFish, getImageBytes(this.context, R.drawable.product_fish));
        fish.save();

        Product ham = new Product(context.getString(R.string.product_ham), meatFish, getImageBytes(this.context, R.drawable.product_ham));
        ham.save();

        Product meat = new Product(context.getString(R.string.product_meat), meatFish, getImageBytes(this.context, R.drawable.product_meat));
        meat.save();

        Product pork = new Product(context.getString(R.string.product_pork), meatFish, getImageBytes(this.context, R.drawable.product_pork));
        pork.save();

        Product salami = new Product(context.getString(R.string.product_salami), meatFish, getImageBytes(this.context, R.drawable.product_salami));
        salami.save();

        Product sardines = new Product(context.getString(R.string.product_sardines), meatFish, getImageBytes(this.context, R.drawable.product_sardines));
        sardines.save();

        Product tuna = new Product(context.getString(R.string.product_tuna), meatFish, getImageBytes(this.context, R.drawable.product_tuna));
        tuna.save();

        // =========================== Frozen =========================== \\

        Category frozen = new Category(context.getString(R.string.category_frozen), Category.COLOR_2);
        frozen.save();

        Product fishSticks = new Product(context.getString(R.string.product_fishSticks), frozen, getImageBytes(this.context, R.drawable.product_fish_sticks));
        fishSticks.save();

        Product frenchFries = new Product(context.getString(R.string.product_frenchFries), frozen, getImageBytes(this.context, R.drawable.product_french_fries));
        frenchFries.save();

        Product iceCream = new Product(context.getString(R.string.product_iceCream), frozen, getImageBytes(this.context, R.drawable.product_ice_cream));
        iceCream.save();

        Product lasagna = new Product(context.getString(R.string.product_lasagna), frozen, getImageBytes(this.context, R.drawable.product_lasagna));
        lasagna.save();

        Product nuggets = new Product(context.getString(R.string.product_nuggets), frozen, getImageBytes(this.context, R.drawable.product_nuggets));
        nuggets.save();

        Product pizza = new Product(context.getString(R.string.product_pizza), frozen, getImageBytes(this.context, R.drawable.product_pizza));
        pizza.save();

        // =========================== Beverage =========================== \\

        Category beverage = new Category(context.getString(R.string.category_beverage), Category.COLOR_5);
        beverage.save();

        Product beer = new Product(context.getString(R.string.product_beer), beverage, getImageBytes(this.context, R.drawable.product_beer));
        beer.save();

        Product coffee = new Product(context.getString(R.string.product_coffee), beverage, getImageBytes(this.context, R.drawable.product_coffee));
        coffee.save();

        Product icedTea = new Product(context.getString(R.string.product_icedTea), beverage, getImageBytes(this.context, R.drawable.product_ice_tea));
        icedTea.save();

        Product soda = new Product(context.getString(R.string.product_soda), beverage, getImageBytes(this.context, R.drawable.product_soda));
        soda.save();

        Product water = new Product(context.getString(R.string.product_water), beverage, getImageBytes(this.context, R.drawable.product_water));
        water.save();

        // =========================== Household =========================== \\

        Category household = new Category(context.getString(R.string.category_household), Category.COLOR_8);
        household.save();

        Product airFreshener = new Product(context.getString(R.string.product_airFreshener), household, getImageBytes(this.context, R.drawable.product_air_freshener));
        airFreshener.save();

        Product bleach = new Product(context.getString(R.string.product_bleach), household, getImageBytes(this.context, R.drawable.product_bleach));
        bleach.save();

        Product bulb = new Product(context.getString(R.string.product_bulb), household, getImageBytes(this.context, R.drawable.product_bulb));
        bulb.save();

        Product cleaningSupplies = new Product(context.getString(R.string.product_cleaningSupplies), household, getImageBytes(this.context, R.drawable.product_cleaning_supplies));
        cleaningSupplies.save();

        Product deodorant = new Product(context.getString(R.string.product_deodorant), household, getImageBytes(this.context, R.drawable.product_deodorant));
        deodorant.save();

        Product dishwashingLiquid = new Product(context.getString(R.string.product_dishwashingLiquid), household, getImageBytes(this.context, R.drawable.product_dishwashing_liquid));
        dishwashingLiquid.save();

        Product hairRemover = new Product(context.getString(R.string.product_hairRemover), household, getImageBytes(this.context, R.drawable.product_hair_remover));
        hairRemover.save();

        Product kitchenRags = new Product(context.getString(R.string.product_kitchenRags), household, getImageBytes(this.context, R.drawable.product_kitchen_rags));
        kitchenRags.save();

        Product garbageBags = new Product(context.getString(R.string.product_garbageBags), household, getImageBytes(this.context, R.drawable.product_garbage_bags));
        garbageBags.save();

        Product laundryDetergent = new Product(context.getString(R.string.product_laundryDetergent), household, getImageBytes(this.context, R.drawable.product_laundry_detergent));
        laundryDetergent.save();

        Product paperTowels = new Product(context.getString(R.string.product_paperTowels), household, getImageBytes(this.context, R.drawable.product_paper_towels));
        paperTowels.save();

        Product razor = new Product(context.getString(R.string.product_razor), household, getImageBytes(this.context, R.drawable.product_razor));
        razor.save();

        Product shampoo = new Product(context.getString(R.string.product_shampoo), household, getImageBytes(this.context, R.drawable.product_shampoo));
        shampoo.save();

        Product shavingFoam = new Product(context.getString(R.string.product_shavingFoam), household, getImageBytes(this.context, R.drawable.product_shaving_foam));
        shavingFoam.save();

        Product showerGel = new Product(context.getString(R.string.product_showerGel), household, getImageBytes(this.context, R.drawable.product_shower_gel));
        showerGel.save();

        Product soap = new Product(context.getString(R.string.product_soap), household, getImageBytes(this.context, R.drawable.product_soap));
        soap.save();

        Product sponge = new Product(context.getString(R.string.product_sponge), household, getImageBytes(this.context, R.drawable.product_sponge_normal));
        sponge.save();

        Product spongeMetal = new Product(context.getString(R.string.product_spongeMetal), household, getImageBytes(this.context, R.drawable.product_sponge_metal));
        spongeMetal.save();

        Product swabs = new Product(context.getString(R.string.product_swabs), household, getImageBytes(this.context, R.drawable.product_swabs));
        swabs.save();

        Product toiletPaper = new Product(context.getString(R.string.product_toiletPaper), household, getImageBytes(this.context, R.drawable.product_toilet_paper));
        toiletPaper.save();

        Product toothbrush = new Product(context.getString(R.string.product_toothbrush), household, getImageBytes(this.context, R.drawable.product_toothbrush));
        toothbrush.save();

        Product toothpaste = new Product(context.getString(R.string.product_toothpaste), household, getImageBytes(this.context, R.drawable.product_toothpaste));
        toothpaste.save();

        return null;
    }

    private byte[] getImageBytes(Context context, int imageId)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        return stream.toByteArray();
    }

    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);

        this.progressDialog.dismiss();
    }
}