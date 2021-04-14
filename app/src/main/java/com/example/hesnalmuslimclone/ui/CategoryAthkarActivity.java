package com.example.hesnalmuslimclone.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.adapter.AthkarAdapter;
import com.example.hesnalmuslimclone.database.HesnDao;
import com.example.hesnalmuslimclone.helper.BaseApplication;
import com.example.hesnalmuslimclone.helper.ThekrClickListener;
import com.example.hesnalmuslimclone.models.Zekr;

import java.util.List;

public class CategoryAthkarActivity extends AppCompatActivity implements ThekrClickListener {
    private static final String TAG = "CategoryAthkarActivity";

    private HesnDao dao;
    private int categoryId;
    private String categoryName;
    private TextView toolbarTextView;
    private ImageView upButtonImageView;
    private RecyclerView athkarRecyclerView;
    private AthkarAdapter athkarAdapter;
    private LinearLayoutManager linearLayoutManager;
    List<Zekr> athkar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_athkar);
        initViews();

        dao = BaseApplication.getDao(getApplicationContext());
        categoryId = getIntent().getIntExtra("id", 0);

        upButtonImageView.setOnClickListener(v -> finish());



        dao.getSpecificCategoryAthkar(categoryId).observe(this, athkar -> {
            this.athkar = athkar;
            athkarAdapter = new AthkarAdapter(athkar, this);
            athkarRecyclerView.setAdapter(athkarAdapter);
        });
    }

    private void initViews() {
        categoryName = getIntent().getStringExtra("categoryName");
        toolbarTextView = findViewById(R.id.toolBarTextView);
        upButtonImageView = findViewById(R.id.upButtonImageView);
        toolbarTextView.setText(categoryName);
        athkarRecyclerView = findViewById(R.id.athkarRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        athkarRecyclerView.setLayoutManager(linearLayoutManager);
        SnapHelper SnapHelper = new PagerSnapHelper();
        SnapHelper.attachToRecyclerView(athkarRecyclerView);

    }

    @Override
    public void onThekrClicked(Zekr zekr, int position) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(250);
        }
        linearLayoutManager.scrollToPosition(position);
    }

    @Override
    public void onFinishingAthkar() {
        Toast.makeText(this, "تمَّ بحَمْدِ اللَّـهِ", Toast.LENGTH_SHORT).show();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            long[] pattern = {0, 500, 500};
            v.vibrate(pattern,2);

        }
    }

    @Override
    public void onThekrLongClicked(Zekr zekr, int position) {
        openThekrUtilsBottomSheet(zekr, categoryName);
    }

    private void openThekrUtilsBottomSheet(Zekr thekr, String categoryName) {
        ThekrUtilsBottomSheet thekrUtilsBottomSheet = new ThekrUtilsBottomSheet(thekr, categoryName);
        thekrUtilsBottomSheet.show(getSupportFragmentManager(), thekrUtilsBottomSheet.getTag());

    }


}