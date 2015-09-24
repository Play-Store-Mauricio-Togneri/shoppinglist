package com.mauriciotogneri.shoppinglist.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.mauriciotogneri.common.base.BaseFragment;
import com.mauriciotogneri.common.base.BaseFragmentActivity;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.fragments.CartFragment;

import java.util.Stack;

public class MainActivity extends BaseFragmentActivity
{
    private final Stack<BaseFragment<?>> fragments = new Stack<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        CartFragment homeFragment = new CartFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, homeFragment);
        fragmentTransaction.commit();

        fragments.add(homeFragment);
    }

    @Override
    public void addFragment(BaseFragment<?> fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();

        fragments.add(fragment);
    }

    @Override
    public void removeFragment()
    {
        BaseFragment<?> currentFragment = fragments.pop();
        Object result = currentFragment.getResult();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(currentFragment);
        transaction.commit();

        BaseFragment<?> previousFragment = fragments.peek();

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
        if (fragments.size() == 1)
        {
            super.onBackPressed();
        }
        else
        {
            removeFragment();
        }
    }
}