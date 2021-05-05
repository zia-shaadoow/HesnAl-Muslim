package com.example.hesnalmuslimclone.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.adapter.CategoriesAdapter;
import com.example.hesnalmuslimclone.database.HesnDao;
import com.example.hesnalmuslimclone.helper.BaseApplication;
import com.example.hesnalmuslimclone.helper.HomeItemClickListener;
import com.example.hesnalmuslimclone.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity implements HomeItemClickListener {
    private static final String TAG = "MainActivity";

    private HesnDao dao;
    private RecyclerView homeRecyclerView;
    private EditText searchEditText;
    private ImageButton favoriteCategoriesImageButton;
    private CategoriesAdapter categoriesAdapter;
    private List<Category> currentCategories;
    private final List<Category> newFilteredCategories = new ArrayList<>();
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        dao = BaseApplication.getDao(getApplicationContext());
        executorService = BaseApplication.getExecutorService();
        //make a simple dummy query to know if there is a database

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable query) {
                if (query.toString().isEmpty()) {
                    getAthkarCategories();
                } else {
                    newFilteredCategories.clear();
                    for (Category category : currentCategories) {
                        if (category.abstractName.contains(query.toString())) {
                            newFilteredCategories.add(category);
                        }
                    }
                    categoriesAdapter = new CategoriesAdapter(newFilteredCategories, MainActivity.this);
                    homeRecyclerView.setAdapter(categoriesAdapter);
                }
            }
        });

        favoriteCategoriesImageButton.setOnClickListener( v -> {
            Intent intent = new Intent(this, FavoriteAthkarActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (categoriesAdapter == null) {
            getAthkarCategories();
        }
    }

    private void initViews() {
        favoriteCategoriesImageButton = findViewById(R.id.favoriteCategoriesImageButton);
        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setSelection(searchEditText.getText().length());
        homeRecyclerView = findViewById(R.id.homeItemsRecyclerView);
    }

    private void getAthkarCategories() {
        LiveData<List<Category>> categoryLiveData = dao.getHomeAthkar();
        categoryLiveData.observe(this, categories -> {
            currentCategories = categories;
            categoriesAdapter = new CategoriesAdapter(categories, this);
            homeRecyclerView.setAdapter(categoriesAdapter);
            //i used this method to prevent live data from emitting new values if i added, or remove from fav.
            categoryLiveData.removeObservers(this);
        });
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
            executorService.execute(() -> dao.removeCategoryFromFavorite(category.id));
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