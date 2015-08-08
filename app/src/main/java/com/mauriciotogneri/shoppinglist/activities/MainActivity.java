package com.mauriciotogneri.shoppinglist.activities;

import java.util.Stack;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.fragments.BaseFragment;
import com.mauriciotogneri.shoppinglist.fragments.CartFragment;

public class MainActivity extends FragmentActivity
{
	private final Stack<BaseFragment<?>> fragments = new Stack<BaseFragment<?>>();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		CartFragment homeFragment = new CartFragment();
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, homeFragment);
		fragmentTransaction.commit();
		
		this.fragments.add(homeFragment);
	}
	
	public void addFragment(BaseFragment<?> fragment)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.fragment_container, fragment);
		transaction.commit();
		
		this.fragments.add(fragment);
	}
	
	public void removeFragment()
	{
		BaseFragment<?> currentFragment = this.fragments.pop();
		Object result = currentFragment.getResult();
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.remove(currentFragment);
		transaction.commit();
		
		BaseFragment<?> previousFragment = this.fragments.peek();
		
		if (result != null)
		{
			previousFragment.onActivate(result);
		}
		else
		{
			previousFragment.onActivate();
		}
	}
	
	@Override
	public void onBackPressed()
	{
		if (this.fragments.size() == 1)
		{
			super.onBackPressed();
		}
		else
		{
			removeFragment();
		}
	}
}