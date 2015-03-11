package com.mauriciotogneri.shoppingcart.activities;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.mauriciotogneri.shoppingcart.R;
import com.mauriciotogneri.shoppingcart.adapters.CategoryAdapter;
import com.mauriciotogneri.shoppingcart.model.Category;
import com.mauriciotogneri.shoppingcart.model.Product;
import com.mauriciotogneri.shoppingcart.widgets.ProductImage;

public class UpdateProductActivity extends Activity
{
	// TODO: MAKE SCROLL VERTICAL
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_product);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		List<Category> categories = Model.all(Category.class);
		
		CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
		
		Spinner category = (Spinner)findViewById(R.id.category);
		category.setAdapter(categoryAdapter);
		category.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		
		TextView toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
		toolbarTitle.setText(R.string.toolbar_title_edit_product);
		
		TextView buttonUpdateText = (TextView)findViewById(R.id.button_update_text);
		buttonUpdateText.setText(R.string.button_edit);
		
		Product product = new Select().from(Product.class).where("id = ?", 1).executeSingle();
		
		ProductImage productImage = (ProductImage)findViewById(R.id.thumbnail);
		productImage.setImage(product.getImage());
		
		LinearLayout buttonUpdate = (LinearLayout)findViewById(R.id.button_update);
		buttonUpdate.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				update();
			}
		});
	}
	
	private void update()
	{
		Toast.makeText(this, "PRESSED!", Toast.LENGTH_SHORT).show();
	}
}