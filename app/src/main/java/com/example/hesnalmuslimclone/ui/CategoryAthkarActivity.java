package com.example.hesnalmuslimclone.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.adapter.AthkarAdapter;
import com.example.hesnalmuslimclone.database.HesnDao;
import com.example.hesnalmuslimclone.exoplayer.RadioPlayerActivity;
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
    private ImageView imageView;
    private RecyclerView athkarRecyclerView;
    private AthkarAdapter athkarAdapter;
    private LinearLayoutManager linearLayoutManager;
    List<Zekr> athkar;

    MediaPlayer mediaPlayer;
    String BASE_THEKR_URL = "https://archive.org/download/hesnalmuslimsounds/";
//    String url = "https://archive.org/download/Husn_Muslim/100.ogg";
    String url = "https://archive.org/download/028_20210407/112.ogg";
//    String url = "https://archive.org/download/hesnalmuslimsounds/112.ogg";
//    String url = "https://archive.org/download/028_20210407/005-.ogg";
//    String url = "https://upload.wikimedia.org/wikipedia/commons/6/6c/Grieg_Lyric_Pieces_Kobold.ogg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_athkar);
        initViews();

        dao = BaseApplication.getDao(getApplicationContext());
        categoryId = getIntent().getIntExtra("id", 0);
        imageView.setOnClickListener(v -> finish());

        dao.getSpecificCategoryAthkar(categoryId).observe(this, athkar -> {
            this.athkar = athkar;
            athkarAdapter = new AthkarAdapter(athkar, this);
            athkarRecyclerView.setAdapter(athkarAdapter);
            Log.i(TAG, "ISLAM onCreate: " + athkar.size());
        });
    }

    private void initViews() {
        categoryName = getIntent().getStringExtra("categoryName");
        toolbarTextView = findViewById(R.id.toolBarTextView);


        imageView = findViewById(R.id.upButtonImageView);
        toolbarTextView.setText(categoryName);
        athkarRecyclerView = findViewById(R.id.athkarRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        athkarRecyclerView.setLayoutManager(linearLayoutManager);
        SnapHelper SnapHelper = new PagerSnapHelper();
        SnapHelper.attachToRecyclerView(athkarRecyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
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
    public void onThekrLongClicked(Zekr zekr, int position) {
        if (zekr.categoryId > 27){
            zekr.categoryId -= 1;
        }

        String thekrUrl = BASE_THEKR_URL + zekr.categoryId + ".ogg";

//        Intent intent = new Intent(this, RadioPlayerActivity.class);
//        intent.putExtra("url", thekrUrl);
//        startActivity(intent);
        openAudioBottomSheet(thekrUrl);

    }



    private void openAudioBottomSheet(String audioUrl){
        AudioPlayerBottomSheet audioPlayerBottomSheet = new AudioPlayerBottomSheet(audioUrl);
        audioPlayerBottomSheet.show(getSupportFragmentManager(), audioPlayerBottomSheet.getTag());
    }
}