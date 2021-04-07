package com.example.hesnalmuslimclone.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "zekr")
public class Zekr {

    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    public Integer categoryId;

    public String description;

    @ColumnInfo(name = "description_abstract")
    @SerializedName("description_abstract")
    public String abstractDescription;

    public String hint;
    public String counter;

    @ColumnInfo(name = "counter_num")
    @SerializedName("counter_num")
    public Integer counterNumber;

    @Ignore
    public Integer partialCounter = 0;

    @Override
    public String toString() {
        return counterNumber + counter+" ";
    }
}
