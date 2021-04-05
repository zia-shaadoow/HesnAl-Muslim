package com.example.hesnalmuslimclone.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey
    public Integer id;

    public String name;

    @ColumnInfo(name = "name_abstract")
    @SerializedName("name_abstract")
    public String abstractName;

    @ColumnInfo(name = "bookmarked")
    @SerializedName("bookmarked")
    public Integer isFavorite;

    public Integer weight;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abstractName='" + abstractName + '\'' +
                ", isFavorite=" + isFavorite +
                ", weight=" + weight +
                '}';
    }
}
