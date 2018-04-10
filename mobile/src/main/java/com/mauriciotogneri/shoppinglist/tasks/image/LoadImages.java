package com.mauriciotogneri.shoppinglist.tasks.image;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    private static final String URL = "https://api.cognitive.microsoft.com/bing/v7.0/images/search?mkt=en-us&q=";

    public LoadImages(OnImagesLoaded callback)
    {
        this.client = new OkHttpClient();
        this.callback = callback;
    }

    @Override
    protected List<String> doInBackground(String... queries)
    {
        Request request = new Request.Builder()
                .url(URL + queries[0])
                .header("Ocp-Apim-Subscription-Key", "c1487859adda4a8a8304c6f42f4e3be6")
                .build();

        try
        {
            Response response = client.newCall(request).execute();
            JsonElement element = new JsonParser().parse(response.body().string());
            JsonArray list = element.getAsJsonObject().getAsJsonArray("value");

            List<String> result = new ArrayList<>();

            for (int i = 0; i < list.size(); i++)
            {
                JsonObject object = list.get(i).getAsJsonObject();

                result.add(object.get("contentUrl").getAsString());
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