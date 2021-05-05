package com.example.hesnalmuslimclone.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.database.HesnDao;
import com.example.hesnalmuslimclone.helper.BaseApplication;
import com.example.hesnalmuslimclone.models.Category;
import com.example.hesnalmuslimclone.models.Zekr;
import com.example.hesnalmuslimclone.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private Handler handler;
    private HesnDao dao;
    private ExecutorService executorService;
    private Zekr thekr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_splash);

        dao = BaseApplication.getDao(getApplicationContext());
        executorService = BaseApplication.getExecutorService();

        handler = new Handler();
        handler.postDelayed(() -> {
            try {
                initializeDb();
                animateAndNavigate();
            }
            catch (Throwable throwable){
                Log.i(TAG, "onCreate: " + throwable.getLocalizedMessage());
            }

        }, 4000);
    }

    private void animateAndNavigate() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void initializeDb() {
        executorService.execute(() -> thekr = dao.getFirstThekr());

        //I blocked the main thread to ensure that the query will be done
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.execute(() -> {
            //no database in the device
            if (thekr == null) {
                Log.i(TAG, "JJJJ initializeDb: " + thekr);
                executorService.execute(() -> {
                    dao.insertCategories(getCategories());
                });
                executorService.execute(() -> {
                    dao.insertAzkar(getAthkar());
                });
            } else {
                Log.i(TAG, "JJJJ initializeDb: " + thekr.id);
            }
        });
    }


    private void makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private List<Category> getCategories() {
        return new Gson().fromJson(Utils.category, new TypeToken<List<Category>>() {
        }.getType());
    }

    private List<Zekr> getAthkar() {
        List<Zekr> athkar = new ArrayList<>();
        for (String thekr : Utils.athkarAsJson) {
            athkar.addAll(new Gson().fromJson(thekr, new TypeToken<List<Zekr>>() {
            }.getType()));
        }
        return athkar;
    }

}