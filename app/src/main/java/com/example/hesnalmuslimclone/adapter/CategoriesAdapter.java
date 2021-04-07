package com.example.hesnalmuslimclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.helper.HomeItemClickListener;
import com.example.hesnalmuslimclone.models.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private final List<Category> categories;
    private final HomeItemClickListener itemClickListener;

    public CategoriesAdapter(List<Category> categories, HomeItemClickListener itemClickListener) {
        this.categories = categories;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category item = categories.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView favoriteImageView;
        TextView categoryNameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            favoriteImageView = itemView.findViewById(R.id.favoriteImageView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            itemView.setOnClickListener(this);

            favoriteImageView.setOnClickListener(v -> {
                Category category = categories.get(getAdapterPosition());
                itemClickListener.onFavoriteIconClicked(getAdapterPosition(), category);
            });
        }

        void bind(Category category){
                categoryNameTextView.setText(category.name);
                int imageResource = (category.isFavorite == 1) ? R.drawable.ic_heart_filled : R.drawable.ic_heart_stroked;
                favoriteImageView.setImageResource(imageResource);
        }

        @Override
        public void onClick(View view) {
            //Dont use if (id == hhahah id ) it is bad and not working
            Category category = categories.get(getAdapterPosition());
            itemClickListener.onItemClicked(category);
        }
    }

}
