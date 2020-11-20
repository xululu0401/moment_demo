package com.jasonxu.simplemoment.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageLoader {

    private static final String TAG = "cus_image_loader";

    public static final int MESSAGE_POST_RESULT = 1;
    private static final int CUP_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CUP_COUNT + 1;
    private static final int MAX_POOL_SIZE = CUP_COUNT*2 + 1;
    private static final long KEEP_ALIVE = 10L;
    private static final int BUFFER_SIZE = 8*1024;
    private static final int DISK_CACHE_SIZE = 50*1024*1024;
    private static final ThreadFactory mThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "Imageloader:  "+mCount.getAndIncrement());
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
            mThreadFactory);

    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            LoaderResult result = (LoaderResult)msg.obj;
            ImageView iv = result.imageview;
            iv.setImageBitmap(result.bitmap);
            // TODO: 2020/11/20 需要处理一下
//            String url = (String)iv.getTag(Tag);

        }
    };

    private Context mContext;
    private ImageOptUtil mImageOptUtil = new ImageOptUtil();
    private LruCache<String, Bitmap> mMemCache;
    private DiskLruCache mDiskCache;
    private boolean mHasDiskCache;

    private ImageLoader(Context context){
        mContext = context.getApplicationContext();
        int maxMem = (int)(Runtime.getRuntime().maxMemory()/1024);
        int cache =maxMem/8;
        mMemCache = new LruCache<String, Bitmap>(cache){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };

        File diskDir = getDiskDir(mContext, "bitmap");
        if (!diskDir.exists()){
            diskDir.mkdirs();
        }
        if (getAvailableSpace(diskDir) > DISK_CACHE_SIZE){
            try {
                mDiskCache = DiskLruCache.open(diskDir, 1, 2, DISK_CACHE_SIZE);
                mHasDiskCache = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private long getAvailableSpace(File path){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }
        StatFs stats = new StatFs(path.getPath());
        return (long)stats.getBlockSize() * (long)stats.getAvailableBlocks();
    }

    private File getDiskDir(Context context, String name) {
        String result = null;
        boolean externalValid = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (externalValid){
            result = context.getExternalCacheDir().getPath();
        } else {
            result = context.getCacheDir().getPath();
        }
        return new File(result);
    }

    public static ImageLoader build(Context ctx){
        return new ImageLoader(ctx);
    }

    private void addBitmapToMemCache(String key, Bitmap bitmap){
        if (mMemCache.get(key) == null) {
            mMemCache.put(key, bitmap);
        }
    }

    public Bitmap loadBitmap(String url, int dstWidth, int dstHeight){
        Bitmap bitmap = loadBitmapFromMemCache(url);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap =loadBitmapFromDisk(url, dstWidth, dstHeight);
        if (null != bitmap) {
            return bitmap;
        }
        bitmap = loadBitmapFromNet(url, dstWidth, dstHeight);
        if (null != bitmap){
            return bitmap;
        }
        bitmap = downloadBitmap(url);
        return bitmap;
    }

    private Bitmap downloadBitmap(String url) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        BufferedInputStream bis = null;
        try{
            URL newUrl = new URL(url);
            connection = (HttpURLConnection) newUrl.openConnection();
            bis = new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(bis);

        } catch (Exception e){
            Log.i(TAG, "error happened:  "+e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (null != bis){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bitmap;
    }

    private Bitmap loadBitmapFromDisk(String url, int dstWidth, int dstHeight){
        if (mDiskCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        String key = getKeyFromurl(url);
        try {
            DiskLruCache.Snapshot snapshot = mDiskCache.get(key);
            if (snapshot != null) {
                FileInputStream is = (FileInputStream)snapshot.getInputStream(0);
                bitmap = mImageOptUtil.getBitmapFromFD(is.getFD(), dstWidth, dstHeight);
                if (null != bitmap){
                    addBitmapToMemCache(key, bitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromNet(String url, int dstWidth, int dstHeight){

        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("cannot request in main Thread");
        }
        if (mDiskCache == null) {
            return null;
        }
        String key = getKeyFromurl(url);
        try {
            DiskLruCache.Editor editor = mDiskCache.edit(key);
            if (null != editor){
                OutputStream os = editor.newOutputStream(0);
                if (downloadToStream(url, os)){
                    editor.commit();
                } else {
                    editor.abort();
                }
                mDiskCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadBitmapFromDisk(url, dstWidth, dstHeight);
    }

    private boolean downloadToStream(String url, OutputStream os) {
        HttpURLConnection connection = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            URL newUrl = new URL(url);
            connection = (HttpURLConnection) newUrl.openConnection();
            bis =new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE);
            bos =new BufferedOutputStream(os, BUFFER_SIZE);
            int b;
            while ((b = bis.read()) != -1){
                bos.write(b);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != connection){
                connection.disconnect();
            }
            if (null != bos){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bis){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    private Bitmap loadBitmapFromMemCache(String url){
        final String key = getKeyFromurl(url);
        return mMemCache.get(key);
    }

    private String getKeyFromurl(String url){
        String result;
        try {
            MessageDigest mDigest =MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            result = byte2HexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            result = String.valueOf(url.hashCode());
        }
        return result;
    }

    private String byte2HexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< bytes.length; i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }



    private class LoaderResult {
        public ImageView imageview;
        public String url;
        public Bitmap bitmap;

        public LoaderResult(ImageView iv, String url, Bitmap bitmap){
            this.imageview = iv;
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
