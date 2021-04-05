package com.example.hesnalmuslimclone.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.database.HesnDao;
import com.example.hesnalmuslimclone.helper.BaseApplication;

public class CategoryAthkarActivity extends AppCompatActivity {
    private static final String TAG = "CategoryAthkarActivity";

    private HesnDao dao;
    private int categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_athkar);

        dao = BaseApplication.getDao(getApplicationContext());
        categoryId = getIntent().getIntExtra("id", 0);



        dao.getSpecificCategoryAthkar(categoryId).observe(this, athkar -> {
            Log.i(TAG, "ISLAM onCreate: " + athkar);
        });
    }
}