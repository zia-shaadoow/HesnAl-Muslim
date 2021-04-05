package com.example.hesnalmuslimclone.helper;

import com.example.hesnalmuslimclone.models.Category;

public interface HomeItemClickListener {
    void onItemClicked(Category category);
    void onFavoriteIconClicked(int position, Category category);
}
