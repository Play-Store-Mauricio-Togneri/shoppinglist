package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
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
import android.util.TypedValue;

import com.mauriciotogneri.shoppinglist.fragments.ProductListFragment;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.common.widgets.CustomTypefaceSpan;
import com.mauriciotogneri.common.widgets.Fonts;

import java.util.List;

public class ProductPagerAdapter extends FragmentStatePagerAdapter
{
    private final Context context;
    private final List<Category> categoryList;

    public ProductPagerAdapter(Context context, FragmentManager fragmentManager, List<Category> categoryList)
    {
        super(fragmentManager);

        this.context = context;
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
        int fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(title);

        Typeface font = Fonts.getFont(Fonts.OPEN_SANS);
        ForegroundColorSpan fcs = new ForegroundColorSpan(category.getIntColor());
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(fontSize);
        StyleSpan bss = new StyleSpan(Typeface.BOLD);

        spannableStringBuilder.setSpan(new CustomTypefaceSpan(font), 0, title.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder.setSpan(fcs, 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder.setSpan(bss, 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder.setSpan(ass, 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return spannableStringBuilder;
    }
}