package com.example.hesnalmuslimclone.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hesnalmuslimclone.models.Category;
import com.example.hesnalmuslimclone.models.Zekr;

@Database(entities = {Zekr.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase DATABASE = null;
    public abstract HesnDao getHesnDao();
    public static AppDatabase getDatabase(Context context){
        if (DATABASE == null){
            DATABASE = Room.databaseBuilder(context, AppDatabase.class, "hesnalmuslim").build();
        }
        return DATABASE;
    }

}
