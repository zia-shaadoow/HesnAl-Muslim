package com.example.hesnalmuslimclone.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.adapter.CategoriesAdapter;
import com.example.hesnalmuslimclone.database.HesnDao;
import com.example.hesnalmuslimclone.helper.BaseApplication;
import com.example.hesnalmuslimclone.helper.HomeItemClickListener;
import com.example.hesnalmuslimclone.models.Category;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class FavoriteAthkarActivity extends AppCompatActivity implements HomeItemClickListener {
    private HesnDao dao;
    private CategoriesAdapter categoriesAdapter;
    private ExecutorService executorService;

    private RecyclerView favoriteCategoriesRecyclerView;
    private ImageButton upButtonImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_athkar);
        initViews();

        dao = BaseApplication.getDao(getApplicationContext());
        executorService = BaseApplication.getExecutorService();

        upButtonImageButton.setOnClickListener(v -> finish());

    }

    @Override
    protected void onStart() {
        super.onStart();
        LiveData<List<Category>> favoriteCategoriesLiveData = dao.getFavoriteAthkar();

        favoriteCategoriesLiveData.observe(this, favoriteCategories -> {
            categoriesAdapter = new CategoriesAdapter(favoriteCategories, this);
            favoriteCategoriesRecyclerView.setAdapter(categoriesAdapter);
            //i used this method to prevent live data from emitting new values if i added, or removed from fav.
            favoriteCategoriesLiveData.removeObservers(this);
        });
    }

    private void initViews(){
        favoriteCategoriesRecyclerView = findViewById(R.id.favoriteCategoriesRecyclerView);
        upButtonImageButton = findViewById(R.id.upButtonImageButton);
    }

    @Override
    public void onItemClicked(Category category) {
        Intent intent = new Intent(this, CategoryAthkarActivity.class);
        intent.putExtra("id", category.id);
        intent.putExtra("categoryName", category.abstractName);
        startActivity(intent);

    }

    @Override
    public void onFavoriteIconClicked(int position, Category category) {
        //Remove from favorite
        if (category.isFavorite == 1) {
            executorService.execute(() -> dao.removeCategoryToFavorite(category.id));
            category.isFavorite = 0;
        }
        //Add to favorite
        else {
            executorService.execute(() -> dao.addCategoryToFavorite(category.id));
            category.isFavorite = 1;
        }
        categoriesAdapter.notifyItemChanged(position, category);
    }
}