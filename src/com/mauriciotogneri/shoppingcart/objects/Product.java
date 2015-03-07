package com.mauriciotogneri.shoppingcart.objects;

import java.io.Serializable;
import com.mauriciotogneri.shoppingcart.R;

public class Product implements Serializable
{
	private static final long serialVersionUID = -4443584456315075370L;
	
	private final int id;
	private final String name;
	private final Type type;
	private int quantity = 0;
	private boolean selected = false;
	
	public enum Category
	{
		DRINK(//
		    Type.MILK, //
		    Type.WATER, //
		    Type.ICE_TEA, //
		    Type.COCA_COLA), //
		
		FOOD(//
		    Type.BANANAS, //
		    Type.POTATOES, //
		    Type.CEREALS, //
		    Type.PASTA, //
		    Type.RICE, //
		    Type.SARDINES, //
		    Type.TOMATO_SAUCE, //
		    Type.TUNA, //
		    Type.CORN, //
		    Type.MEAT, //
		    Type.EGGS, //
		    Type.FISH_STICKS, //
		    Type.SUNFLOWER_OIL, //
		    Type.SALT, //
		    Type.SOUP, //
		    Type.GRATED_CHEESE, //
		    Type.PIZZA), //
		
		KITCHEN(//
		    Type.KITCHEN_PAPER,//
		    Type.KITCHEN_RAGS,//
		    Type.SPONGE_NORMAL,//
		    Type.SPONGE_METAL,//
		    Type.WASHINGUP_LIQUID,//
		    Type.TRASH_BAG_ORGANIC, //
		    Type.TRASH_BAG_PLASTIC), //
		
		BATHROOM(//
		    Type.TOILET_PAPER, //
		    Type.SWABS, //
		    Type.RAZOR, //
		    Type.SHAVING_FOAM, //
		    Type.SOAP, //
		    Type.SHAMPOO, //
		    Type.SHOWER_GEL, //
		    Type.DEODORANT, //
		    Type.AIR_FRESHENER, //
		    Type.TOOTHBRUSH, //
		    Type.TOOTHPASTE), //
		
		CLOTHES(//
		    Type.DETERGENT, //
		    Type.HAIR_REMOVER); //
		
		private final Type[] types;
		
		private Category(Type... types)
		{
			this.types = new Type[types.length + 1];
			System.arraycopy(types, 0, this.types, 0, types.length);
			this.types[this.types.length - 1] = Type.OTHER;
		}
		
		public Type[] getTypes()
		{
			return this.types;
		}
		
		public static Category getCategoryFromType(Type value)
		{
			Category result = null;
			
			for (Category category : Category.values())
			{
				Type[] types = category.types;
				
				for (Type type : types)
				{
					if (type == value)
					{
						result = category;
						break;
					}
				}
			}
			
			return result;
		}
		
		@Override
		public String toString()
		{
			String value = super.toString();
			
			return value.charAt(0) + value.substring(1).toLowerCase();
		}
	}
	
	public enum Type
	{
		// DRINK
		MILK(R.drawable.product_milk), //
		WATER(R.drawable.product_water), //
		COCA_COLA(R.drawable.product_coca_cola), //
		ICE_TEA(R.drawable.product_ice_tea), //
		
		// FOOD
		BANANAS(R.drawable.product_bananas), //
		POTATOES(R.drawable.product_potatoes), //
		CEREALS(R.drawable.product_cereals), //
		PASTA(R.drawable.product_pasta), //
		RICE(R.drawable.product_rice), //
		SARDINES(R.drawable.product_sardines), //
		TUNA(R.drawable.product_tuna), //
		CORN(R.drawable.product_corn), //
		MEAT(R.drawable.product_meat), //
		EGGS(R.drawable.product_eggs), //
		FISH_STICKS(R.drawable.product_fish_sticks), //
		SUNFLOWER_OIL(R.drawable.product_sunflower_oil), //
		SALT(R.drawable.product_salt), //
		SOUP(R.drawable.product_soup), //
		TOMATO_SAUCE(R.drawable.product_tomato_sauce), //
		GRATED_CHEESE(R.drawable.product_grated_cheese), //
		PIZZA(R.drawable.product_pizza), //
		
		// KITCHEN
		KITCHEN_PAPER(R.drawable.product_kitchen_paper), //
		KITCHEN_RAGS(R.drawable.product_kitchen_rags), //
		SPONGE_NORMAL(R.drawable.product_sponge_normal), //
		SPONGE_METAL(R.drawable.product_sponge_metal), //
		WASHINGUP_LIQUID(R.drawable.product_washingup_liquid), //
		TRASH_BAG_ORGANIC(R.drawable.product_trash_bag_organic), //
		TRASH_BAG_PLASTIC(R.drawable.product_trash_bag_plastic), //
		
		// BATHROOM
		TOILET_PAPER(R.drawable.product_toilet_paper), //
		TOOTHBRUSH(R.drawable.product_toothbrush), //
		TOOTHPASTE(R.drawable.product_toothpaste), //
		RAZOR(R.drawable.product_razor), //
		SHAVING_FOAM(R.drawable.product_shaving_foam), //
		SOAP(R.drawable.product_soap), //
		DEODORANT(R.drawable.product_deodorant), //
		SHAMPOO(R.drawable.product_shampoo), //
		SHOWER_GEL(R.drawable.product_shower_gel), //
		AIR_FRESHENER(R.drawable.product_air_freshener), //
		SWABS(R.drawable.product_swabs), //
		
		// CLOTHES
		DETERGENT(R.drawable.product_detergent), //
		HAIR_REMOVER(R.drawable.product_hair_remover), //
		
		// OTHER
		OTHER(R.drawable.product_other); //
		
		private final int resourceId;
		
		private Type(int resourceId)
		{
			this.resourceId = resourceId;
		}
		
		public int getThumbnail()
		{
			return this.resourceId;
		}
		
		@Override
		public String toString()
		{
			String value = super.toString();
			
			return value.charAt(0) + value.substring(1).toLowerCase().replace("_", " ");
		}
		
		public String toFieldValue()
		{
			return super.toString();
		}
	}
	
	public Product(int id, String name, Type type, int quantity, boolean selected)
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.quantity = quantity;
		this.selected = selected;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public int getThumbnail()
	{
		return this.type.getThumbnail();
	}
	
	public int getQuantity()
	{
		return this.quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public boolean isSelected()
	{
		return this.selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	public boolean isType(Type[] types)
	{
		boolean result = false;
		
		for (Type currentType : types)
		{
			if (currentType == this.type)
			{
				result = true;
				break;
			}
		}
		
		return result;
	}
}