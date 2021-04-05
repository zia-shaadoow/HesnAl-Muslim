package com.example.hesnalmuslimclone.ui;

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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeItemViewHolder> {
    List<Category> homeItems;
    HomeItemClickListener itemClickListener;

    public HomeAdapter(List<Category> homeItems, HomeItemClickListener itemClickListener) {
        this.homeItems = homeItems;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new HomeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position) {
        Category item = homeItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    class HomeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView favoriteImageView;
        TextView categoryNameTextView;

        public HomeItemViewHolder(@NonNull View itemView) {
            super(itemView);

            favoriteImageView = itemView.findViewById(R.id.favoriteImageView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            itemView.setOnClickListener(this);

            favoriteImageView.setOnClickListener(v -> {
                Category category = homeItems.get(getAdapterPosition());
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
            Category category = homeItems.get(getAdapterPosition());
                itemClickListener.onItemClicked(category);
        }
    }

}
