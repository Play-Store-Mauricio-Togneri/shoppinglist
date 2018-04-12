package com.mauriciotogneri.shoppinglist.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mauriciotogneri.androidutils.Screen;
import com.mauriciotogneri.androidutils.Screen.ScreenSize;

import java.util.ArrayList;
import java.util.List;

public class SearchImageAdapter extends BaseAdapter
{
    private final Context context;
    private final List<String> images;
    private final RequestManager imageLoader;

    public SearchImageAdapter(Context context)
    {
        this.context = context;
        this.images = new ArrayList<>();
        this.imageLoader = Glide.with(context);
    }

    public void loadImages(List<String> list)
    {
        images.clear();
        images.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return images.size();
    }

    @Override
    public Object getItem(int position)
    {
        return images.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return images.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if (convertView == null)
        {
            ScreenSize screenSize = new Screen(context).size();

            int size = screenSize.width() / 3;
            int padding = (int) ((float) size / 20f);

            imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(padding, padding, padding, padding);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageLoader.load(images.get(position)).into(imageView);

        return imageView;
    }
}