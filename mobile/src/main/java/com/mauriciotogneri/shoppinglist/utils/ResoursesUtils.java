package com.mauriciotogneri.shoppinglist.utils;

import android.content.Context;
import android.net.Uri;

import com.mauriciotogneri.javautils.Streams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ResoursesUtils
{
    public static File fileFromUri(Context context, Uri uri) throws Exception
    {
        File file = File.createTempFile("gallery", "", context.getCacheDir());
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        OutputStream outputStream = new FileOutputStream(file);

        Streams.copy(inputStream, outputStream);

        return file;
    }
}