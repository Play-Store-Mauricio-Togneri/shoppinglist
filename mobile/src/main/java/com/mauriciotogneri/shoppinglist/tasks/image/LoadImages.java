package com.mauriciotogneri.shoppinglist.tasks.image;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mauriciotogneri.javautils.Encoding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadImages extends AsyncTask<String, Void, List<String>>
{
    private final OkHttpClient client;
    private final OnImagesLoaded callback;

    private static final String URL = "https://pixabay.com/api/?key=8916913-905d7b5a1d35466fd2341bc9c&per_page=99&image_type=illustration&q=";

    public LoadImages(OnImagesLoaded callback)
    {
        this.client = new OkHttpClient();
        this.callback = callback;
    }

    @Override
    protected List<String> doInBackground(String... queries)
    {
        try
        {
            Request request = new Request.Builder()
                    .url(URL + Encoding.urlEncode(queries[0]))
                    .build();

            Response response = client.newCall(request).execute();
            JsonElement element = new JsonParser().parse(response.body().string());
            JsonArray list = element.getAsJsonObject().getAsJsonArray("hits");

            List<String> result = new ArrayList<>();

            for (int i = 0; i < list.size(); i++)
            {
                JsonObject object = list.get(i).getAsJsonObject();

                result.add(object.get("webformatURL").getAsString());
            }

            return result;
        }
        catch (Exception e)
        {
            return Arrays.asList();
        }
    }

    @Override
    protected void onPostExecute(List<String> images)
    {
        callback.onImagesLoaded(images);
    }

    public interface OnImagesLoaded
    {
        void onImagesLoaded(List<String> images);
    }
}