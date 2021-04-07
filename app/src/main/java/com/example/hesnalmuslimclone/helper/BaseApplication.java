package com.example.hesnalmuslimclone.helper;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.hesnalmuslimclone.database.AppDatabase;
import com.example.hesnalmuslimclone.database.HesnDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "SSS onCreate: " );
    }

    public static HesnDao getDao (Context context){
       return AppDatabase.getDatabase(context).getHesnDao();
    }

    public static ExecutorService getExecutorService (){
        return  Executors.newSingleThreadExecutor();
    }
}
