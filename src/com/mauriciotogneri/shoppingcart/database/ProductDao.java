package com.mauriciotogneri.shoppingcart.database;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mauriciotogneri.shoppingcart.objects.Product;
import com.mauriciotogneri.shoppingcart.objects.Product.Type;

public class ProductDao
{
	private static final String TABLE_NAME = "PRODUCT";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_TYPE = "type";
	private static final String COLUMN_QUANTITY = "quantity";
	private static final String COLUMN_SELECTED = "selected";
	
	public List<Product> getProducts(boolean inList)
	{
		List<Product> result = new ArrayList<Product>();

		SQLiteDatabase database = null;
		Cursor cursor = null;
		
		try
		{
			database = Database.getInstance();
			// insertRows(database);
			
			String[] columns = new String[]
				{
					ProductDao.COLUMN_ID, ProductDao.COLUMN_NAME, ProductDao.COLUMN_TYPE, ProductDao.COLUMN_QUANTITY, ProductDao.COLUMN_SELECTED
				};

			String selection = "";

			if (inList)
			{
				selection = ProductDao.COLUMN_QUANTITY + " > 0";
			}
			else
			{
				selection = ProductDao.COLUMN_QUANTITY + " = 0";
			}

			cursor = database.query(ProductDao.TABLE_NAME, columns, selection, null, null, null, null);
			
			cursor.moveToFirst();

			while (!cursor.isAfterLast())
			{
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				Type type = Type.valueOf(cursor.getString(2));
				int quantity = cursor.getInt(3);
				boolean selected = cursor.getInt(4) > 0;

				Product product = new Product(id, name, type, quantity, selected);
				result.add(product);

				cursor.moveToNext();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.closeCursor(cursor);
			Database.closeDatabase(database);
		}
		
		return result;
	}
	
	public boolean updateProduct(Product product)
	{
		boolean result = false;
		SQLiteDatabase database = null;

		try
		{
			database = Database.getInstance();

			ContentValues values = new ContentValues();
			values.put(ProductDao.COLUMN_NAME, product.getName().trim());
			values.put(ProductDao.COLUMN_TYPE, product.getType().toFieldValue());
			values.put(ProductDao.COLUMN_SELECTED, product.isSelected());
			values.put(ProductDao.COLUMN_QUANTITY, product.getQuantity());
			
			String whereClause = ProductDao.COLUMN_ID + " = ?";
			String[] whereArgs = new String[]
				{
					String.valueOf(product.getId())
				};
			
			int rowsAffected = database.update(ProductDao.TABLE_NAME, values, whereClause, whereArgs);
			
			result = (rowsAffected == 1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.closeDatabase(database);
		}
		
		return result;
	}

	public Product createProduct(String name, Type type)
	{
		Product result = null;
		SQLiteDatabase database = null;

		try
		{
			database = Database.getInstance();

			ContentValues values = new ContentValues();
			values.put(ProductDao.COLUMN_NAME, name.trim());
			values.put(ProductDao.COLUMN_TYPE, type.toFieldValue());
			values.put(ProductDao.COLUMN_SELECTED, false);
			values.put(ProductDao.COLUMN_QUANTITY, 0);
			
			long id = database.insert(ProductDao.TABLE_NAME, null, values);
			
			if (id > 0)
			{
				result = new Product((int)id, name, type, 0, false);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.closeDatabase(database);
		}

		return result;
	}
	
	public boolean removeProduct(Product product)
	{
		boolean result = false;
		SQLiteDatabase database = null;

		try
		{
			database = Database.getInstance();

			String whereClause = ProductDao.COLUMN_ID + " = ?";
			String[] whereArgs = new String[]
				{
					String.valueOf(product.getId())
				};
			
			int rowsAffected = database.delete(ProductDao.TABLE_NAME, whereClause, whereArgs);
			
			result = (rowsAffected == 1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.closeDatabase(database);
		}

		return result;
	}
	
	@SuppressWarnings("unused")
	private void insertRows(SQLiteDatabase database)
	{
		// DRINK
		insertProduct(database, "Milk", Type.MILK);
		insertProduct(database, "Water", Type.WATER);
		insertProduct(database, "Coca-Cola", Type.COCA_COLA);
		insertProduct(database, "Ice Tea", Type.ICE_TEA);

		// FOOD
		insertProduct(database, "Bananas", Type.BANANAS);
		insertProduct(database, "Potatoes", Type.POTATOES);
		insertProduct(database, "Cereals", Type.CEREALS);
		insertProduct(database, "Pasta", Type.PASTA);
		insertProduct(database, "Rice", Type.RICE);
		insertProduct(database, "Sardines", Type.SARDINES);
		insertProduct(database, "Tomato Sauce", Type.TOMATO_SAUCE);
		insertProduct(database, "Tuna", Type.TUNA);
		insertProduct(database, "Corn", Type.CORN);
		insertProduct(database, "Meat", Type.MEAT);
		insertProduct(database, "Eggs", Type.EGGS);
		insertProduct(database, "Fish Sticks", Type.FISH_STICKS);
		insertProduct(database, "Sunflower Oil", Type.SUNFLOWER_OIL);
		insertProduct(database, "Salt", Type.SALT);
		insertProduct(database, "Soup", Type.SOUP);
		insertProduct(database, "Grated Cheese", Type.GRATED_CHEESE);
		insertProduct(database, "Pizza", Type.PIZZA);

		// KITCHEN
		insertProduct(database, "Kitchen Paper", Type.KITCHEN_PAPER);
		insertProduct(database, "Kitchen Rags", Type.KITCHEN_RAGS);
		insertProduct(database, "Sponge Normal", Type.SPONGE_NORMAL);
		insertProduct(database, "Sponge Metal", Type.SPONGE_METAL);
		insertProduct(database, "Whasing-Up Liquid", Type.WASHINGUP_LIQUID);
		insertProduct(database, "Plastic Bag", Type.TRASH_BAG_PLASTIC);
		insertProduct(database, "Organic Bag", Type.TRASH_BAG_ORGANIC);

		// BATHROOM
		insertProduct(database, "Toilet Paper", Type.TOILET_PAPER);
		insertProduct(database, "Toothbrush", Type.TOOTHBRUSH);
		insertProduct(database, "Toothpaste", Type.TOOTHPASTE);
		insertProduct(database, "Razor", Type.RAZOR);
		insertProduct(database, "Shaving Foam", Type.SHAVING_FOAM);
		insertProduct(database, "Soap", Type.SOAP);
		insertProduct(database, "Deodorant", Type.DEODORANT);
		insertProduct(database, "Shampoo", Type.SHAMPOO);
		insertProduct(database, "Shower Gel", Type.SHOWER_GEL);
		insertProduct(database, "Air Freshener", Type.AIR_FRESHENER);
		insertProduct(database, "Swabs", Type.SWABS);

		// CLOTHES
		insertProduct(database, "Detergent", Type.DETERGENT);
		insertProduct(database, "Hair Remover", Type.HAIR_REMOVER);
	}

	private void insertProduct(SQLiteDatabase database, String name, Type type)
	{
		ContentValues values = new ContentValues();
		values.put(ProductDao.COLUMN_NAME, name);
		values.put(ProductDao.COLUMN_TYPE, type.toFieldValue());
		values.put(ProductDao.COLUMN_QUANTITY, 0);
		values.put(ProductDao.COLUMN_SELECTED, false);
		database.insert(ProductDao.TABLE_NAME, null, values);
	}
}