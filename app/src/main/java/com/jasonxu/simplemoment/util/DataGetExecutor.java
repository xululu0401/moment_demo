package com.jasonxu.simplemoment.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class DataGetExecutor {

    private static final String TAG = "DataGetExecutor";

    private static DataGetExecutor mExecutor = new DataGetExecutor();

    private static Handler mHandler;
    private DataGetExecutor(){
        HandlerThread thread = new HandlerThread(TAG);
        thread.start();
        mHandler =new Handler(thread.getLooper());
    }

    public static void execute(Runnable runnable){
        if (Looper.myLooper() == mHandler.getLooper()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }
}
