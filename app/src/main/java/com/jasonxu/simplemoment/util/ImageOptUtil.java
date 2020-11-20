package com.jasonxu.simplemoment.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

//this util is used to resize the bitmap
public class ImageOptUtil {

    private static final String TAG = "image option util";

    public ImageOptUtil() {

    }

    public Bitmap getBitFromRes(Resources res, int resId, int dstWidth, int dstHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId);
        options.inSampleSize = calcuteSample(options, dstWidth, dstHeight);
        options.inJustDecodeBounds =false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public Bitmap getBitmapFromFD(FileDescriptor fd, int dstWidth, int dstHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calcuteSample(options, dstWidth, dstHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    private int calcuteSample(BitmapFactory.Options options, int dstWidth, int dstHeight) {
        if (dstWidth == 0 || dstHeight == 0) {
            return 1;
        }

        final int oriWidth = options.outWidth;
        final int oriHeight = options.outHeight;
        int inSampleSize = 1;
        if (oriWidth > dstWidth || oriHeight > dstHeight) {
            int halfOriWidth = oriWidth / 2;
            int halfOriHeight = oriHeight / 2;
            while (halfOriHeight / inSampleSize >= dstHeight && halfOriWidth / inSampleSize >= dstWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
