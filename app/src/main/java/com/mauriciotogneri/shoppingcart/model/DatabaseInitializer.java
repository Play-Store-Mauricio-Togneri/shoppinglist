package com.mauriciotogneri.shoppingcart.model;

import java.io.ByteArrayOutputStream;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.mauriciotogneri.shoppingcart.R;

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
		
		Category fruitsVegetables = new Category("Fruits & Vegetables", Category.COLOR_3);
		fruitsVegetables.save();
		
		Product apples = new Product("Apples", fruitsVegetables, getImageBytes(this.context, R.drawable.product_apples));
		apples.save();
		
		Product bananas = new Product("Bananas", fruitsVegetables, getImageBytes(this.context, R.drawable.product_bananas));
		bananas.save();
		
		Product carrots = new Product("Carrots", fruitsVegetables, getImageBytes(this.context, R.drawable.product_carrots));
		carrots.save();
		
		Product lemons = new Product("Lemons", fruitsVegetables, getImageBytes(this.context, R.drawable.product_lemons));
		lemons.save();
		
		Product lettuce = new Product("Lettuce", fruitsVegetables, getImageBytes(this.context, R.drawable.product_lettuce));
		lettuce.save();
		
		Product pineapple = new Product("Pineapple", fruitsVegetables, getImageBytes(this.context, R.drawable.product_pineapple));
		pineapple.save();
		
		Product potatoes = new Product("Potatoes", fruitsVegetables, getImageBytes(this.context, R.drawable.product_potatoes));
		potatoes.save();
		
		// =========================== Bread & Grain Products =========================== \\
		
		Category breadGrainProducts = new Category("Bread & Grain Products", Category.COLOR_6);
		breadGrainProducts.save();
		
		Product baguette = new Product("Baguette", breadGrainProducts, getImageBytes(this.context, R.drawable.product_baguette));
		baguette.save();
		
		Product cereals = new Product("Cereals", breadGrainProducts, getImageBytes(this.context, R.drawable.product_cereals));
		cereals.save();
		
		Product pasta = new Product("Pasta", breadGrainProducts, getImageBytes(this.context, R.drawable.product_pasta));
		pasta.save();
		
		Product rice = new Product("Rice", breadGrainProducts, getImageBytes(this.context, R.drawable.product_rice));
		rice.save();
		
		Product slicedBread = new Product("Sliced bread", breadGrainProducts, getImageBytes(this.context, R.drawable.product_sliced_bread));
		slicedBread.save();
		
		// =========================== Milk & Cheese =========================== \\
		
		Category milkCheese = new Category("Milk & Cheese", Category.COLOR_7);
		milkCheese.save();
		
		Product cheese = new Product("Cheese", milkCheese, getImageBytes(this.context, R.drawable.product_cheese));
		cheese.save();
		
		Product eggs = new Product("Eggs", milkCheese, getImageBytes(this.context, R.drawable.product_eggs));
		eggs.save();
		
		Product milk = new Product("Milk", milkCheese, getImageBytes(this.context, R.drawable.product_milk));
		milk.save();
		
		Product yogurt = new Product("Yogurt", milkCheese, getImageBytes(this.context, R.drawable.product_yogurt));
		yogurt.save();
		
		// =========================== Meat & Fish =========================== \\
		
		Category meatFish = new Category("Meat & Fish", Category.COLOR_4);
		meatFish.save();
		
		Product chicken = new Product("Chicken", meatFish, getImageBytes(this.context, R.drawable.product_chicken));
		chicken.save();
		
		Product fish = new Product("Fish", meatFish, getImageBytes(this.context, R.drawable.product_fish));
		fish.save();
		
		Product ham = new Product("Ham", meatFish, getImageBytes(this.context, R.drawable.product_ham));
		ham.save();
		
		Product meat = new Product("Meat", meatFish, getImageBytes(this.context, R.drawable.product_meat));
		meat.save();
		
		Product pork = new Product("Pork", meatFish, getImageBytes(this.context, R.drawable.product_pork));
		pork.save();
		
		Product salami = new Product("Salami", meatFish, getImageBytes(this.context, R.drawable.product_salami));
		salami.save();
		
		Product sardines = new Product("Sardines", meatFish, getImageBytes(this.context, R.drawable.product_sardines));
		sardines.save();
		
		Product tuna = new Product("Tuna", meatFish, getImageBytes(this.context, R.drawable.product_tuna));
		tuna.save();
		
		// =========================== Frozen =========================== \\
		
		Category frozen = new Category("Frozen", Category.COLOR_2);
		frozen.save();
		
		Product fishSticks = new Product("Fish sticks", frozen, getImageBytes(this.context, R.drawable.product_fish_sticks));
		fishSticks.save();
		
		Product frenchFries = new Product("French fries", frozen, getImageBytes(this.context, R.drawable.product_french_fries));
		frenchFries.save();
		
		Product iceCream = new Product("Ice cream", frozen, getImageBytes(this.context, R.drawable.product_ice_cream));
		iceCream.save();
		
		Product lasagna = new Product("Lasagna", frozen, getImageBytes(this.context, R.drawable.product_lasagna));
		lasagna.save();
		
		Product nuggets = new Product("Nuggets", frozen, getImageBytes(this.context, R.drawable.product_nuggets));
		nuggets.save();
		
		Product pizza = new Product("Pizza", frozen, getImageBytes(this.context, R.drawable.product_pizza));
		pizza.save();
		
		// =========================== Beverage =========================== \\
		
		Category beverage = new Category("Beverage", Category.COLOR_5);
		beverage.save();
		
		Product beer = new Product("Beer", beverage, getImageBytes(this.context, R.drawable.product_beer));
		beer.save();
		
		Product coffee = new Product("Coffee", beverage, getImageBytes(this.context, R.drawable.product_coffee));
		coffee.save();
		
		Product icedTea = new Product("Iced tea", beverage, getImageBytes(this.context, R.drawable.product_ice_tea));
		icedTea.save();
		
		Product soda = new Product("Soda", beverage, getImageBytes(this.context, R.drawable.product_soda));
		soda.save();
		
		Product water = new Product("Water", beverage, getImageBytes(this.context, R.drawable.product_water));
		water.save();
		
		// =========================== Household =========================== \\
		
		Category household = new Category("Household", Category.COLOR_8);
		household.save();
		
		Product cleaningSupplies = new Product("Cleaning supplies", household, getImageBytes(this.context, R.drawable.product_cleaning_supplies));
		cleaningSupplies.save();
		
		Product deodorant = new Product("Deodorant", household, getImageBytes(this.context, R.drawable.product_deodorant));
		deodorant.save();
		
		Product dishwashingLiquid = new Product("Dishwashing liquid", household, getImageBytes(this.context, R.drawable.product_dishwashing_liquid));
		dishwashingLiquid.save();
		
		Product garbageBags = new Product("Garbage bags", household, getImageBytes(this.context, R.drawable.product_garbage_bags));
		garbageBags.save();
		
		Product laundryDetergent = new Product("Laundry Detergent", household, getImageBytes(this.context, R.drawable.product_laundry_detergent));
		laundryDetergent.save();
		
		Product paperTowels = new Product("Paper towels", household, getImageBytes(this.context, R.drawable.product_paper_towels));
		paperTowels.save();
		
		Product razor = new Product("Razor", household, getImageBytes(this.context, R.drawable.product_razor));
		razor.save();
		
		Product shampoo = new Product("Shampoo", household, getImageBytes(this.context, R.drawable.product_shampoo));
		shampoo.save();
		
		Product soap = new Product("Soap", household, getImageBytes(this.context, R.drawable.product_soap));
		soap.save();
		
		Product sponge = new Product("Sponge", household, getImageBytes(this.context, R.drawable.product_sponge_normal));
		sponge.save();
		
		Product swabs = new Product("Swabs", household, getImageBytes(this.context, R.drawable.product_swabs));
		swabs.save();
		
		Product toiletPaper = new Product("Toilet paper", household, getImageBytes(this.context, R.drawable.product_toilet_paper));
		toiletPaper.save();
		
		Product toothbrush = new Product("Toothbrush", household, getImageBytes(this.context, R.drawable.product_toothbrush));
		toothbrush.save();
		
		Product toothpaste = new Product("Toothpaste", household, getImageBytes(this.context, R.drawable.product_toothpaste));
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