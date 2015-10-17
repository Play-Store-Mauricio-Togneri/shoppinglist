package com.mauriciotogneri.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageHelper
{
    private ImageHelper()
    {
    }

    public static byte[] getImageBytesFromResource(Context context, int imageId)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        return stream.toByteArray();
    }

    public static byte[] getImageBytesFromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        return stream.toByteArray();
    }

    public static Bitmap getBitmapFromBytes(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] getImageBytesFromUri(Context context, Uri uri) throws IOException
    {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        byte[] inputData = getBytes(inputStream);

        if (inputStream != null)
        {
            inputStream.close();
        }

        return inputData;
    }

    private static byte[] getBytes(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int read;

        while ((read = inputStream.read(buffer)) != -1)
        {
            byteBuffer.write(buffer, 0, read);
        }

        return byteBuffer.toByteArray();
    }
}