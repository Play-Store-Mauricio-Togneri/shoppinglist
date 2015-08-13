package com.mauriciotogneri.shoppinglist.adapters;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.mauriciotogneri.shoppinglist.fragments.ProductListFragment;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.widgets.CustomTypefaceSpan;
import com.mauriciotogneri.shoppinglist.widgets.Fonts;

import java.util.List;

public class ProductPagerAdapter extends FragmentStatePagerAdapter
{
    private final List<Category> categoryList;

    public ProductPagerAdapter(FragmentManager fragmentManager, List<Category> categoryList)
    {
        super(fragmentManager);

        this.categoryList = categoryList;
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position < categoryList.size())
        {
            Category category = categoryList.get(position);

            return ProductListFragment.getInstance(category);
        }

        return null;
    }

    @Override
    public int getCount()
    {
        return categoryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        Category category = categoryList.get(position);
        String title = category.getName();

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(title);

        Typeface font = Fonts.getFont(Fonts.OPEN_SANS);
        ForegroundColorSpan fcs = new ForegroundColorSpan(category.getIntColor());
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(25);
        StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        spannableStringBuilder.setSpan(new CustomTypefaceSpan(font), 0, title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.setSpan(fcs, 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder.setSpan(bss, 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder.setSpan(ass, 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//resize size

        return spannableStringBuilder;
    }
}