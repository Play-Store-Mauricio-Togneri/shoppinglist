package com.mauriciotogneri.shoppinglist.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.mauriciotogneri.javautils.Streams;
import com.mauriciotogneri.shoppinglist.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ResourceUtils
{
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    public static Uri uri(Context context) throws Exception
    {
        return FileProvider.getUriForFile(context, AUTHORITY, tempFile(context));
    }

    public static File fileFromUri(Context context, Uri uri) throws Exception
    {
        File file = tempFile(context);
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        OutputStream outputStream = new FileOutputStream(file);

        Streams.copy(inputStream, outputStream);

        return file;
    }

    private static File tempFile(Context context) throws Exception
    {
        return File.createTempFile("temp", "", context.getCacheDir());
    }
}