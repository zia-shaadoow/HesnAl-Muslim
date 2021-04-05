package com.example.hesnalmuslimclone.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "zekr")
public class Zekr {

    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    public Integer category_id;

    public String description;

    @ColumnInfo(name = "description_abstract")
    @SerializedName("description_abstract")
    public String abstractDescription;

    public String hint;
    public String counter;

    @ColumnInfo(name = "counter_num")
    @SerializedName("counter_num")
    public Integer counterNumber;

    @Override
    public String toString() {
        return "Zekr{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", description='" + description + '\'' +
                ", abstractDescription='" + abstractDescription + '\'' +
                ", hint='" + hint + '\'' +
                ", counter='" + counter + '\'' +
                ", counterNumber=" + counterNumber +
                '}';
    }
}
