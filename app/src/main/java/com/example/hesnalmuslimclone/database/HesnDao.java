package com.example.hesnalmuslimclone.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.hesnalmuslimclone.models.Category;
import com.example.hesnalmuslimclone.models.Zekr;

import java.util.List;


@Dao
public interface HesnDao {

    @Transaction
    @Query("SELECT * FROM category ")
    LiveData<List<Category>> getHomeAthkar();

    @Query("SELECT * FROM zekr WHERE category_id = :categoryId")
    LiveData<List<Zekr>> getSpecificCategoryAthkar(int categoryId);

    @Transaction
    @Query("SELECT * FROM category WHERE bookmarked = 1")
    LiveData<List<Category>> getFavoriteAthkar();

    @Query("UPDATE category SET bookmarked = 1 WHERE id = :id")
    void addCategoryToFavorite(int id);

    @Query("UPDATE category SET bookmarked = 0 WHERE id = :id")
    void removeCategoryToFavorite(int id);

    @Query("SELECT * FROM zekr WHERE id = 1")
    Zekr getFirstThekr();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(List<Category> categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAzkar(List<Zekr> azkar);


}
