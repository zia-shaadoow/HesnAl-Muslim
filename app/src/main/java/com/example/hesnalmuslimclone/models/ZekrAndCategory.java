package com.example.hesnalmuslimclone.models;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ZekrAndCategory {

    @Embedded
    public Category category;

    @Relation(
            parentColumn = "id",
            entityColumn = "category_id"
    )
    public List<Zekr> zekr;
}
