package com.mauriciotogneri.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageHelper
{
    private static final int MAX_WIDTH = 64;
    private static final int MAX_HEIGHT = 64;

    private ImageHelper()
    {
    }

    public static Bitmap getBitmapFromByteArray(byte[] image)
    {
        return decodeSampledBitmapFromByteArray(image);
    }

    public static byte[] getByteArrayFromResource(Context context, int imageId)
    {
        Bitmap bitmap = decodeSampledBitmapFromResource(context.getResources(), imageId);

        return bitmapToByteArray(bitmap);
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap)
    {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, MAX_WIDTH, MAX_HEIGHT, true);

        return bitmapToByteArray(scaledBitmap);
    }

    public static byte[] getByteArrayFromUri(Context context, Uri uri) throws IOException
    {
        InputStream inputStream1 = context.getContentResolver().openInputStream(uri);
        InputStream inputStream2 = context.getContentResolver().openInputStream(uri);

        Bitmap bitmap = decodeSampledBitmapFromStream(inputStream1, inputStream2);

        return bitmapToByteArray(bitmap);
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        return stream.toByteArray();
    }

    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId)
    {
        // first decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static Bitmap decodeSampledBitmapFromByteArray(byte[] image)
    {
        // first decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(image, 0, image.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeByteArray(image, 0, image.length, options);
    }

    private static Bitmap decodeSampledBitmapFromStream(InputStream stream1, InputStream stream2)
    {
        // first decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream1, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(stream2, null, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            // calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}