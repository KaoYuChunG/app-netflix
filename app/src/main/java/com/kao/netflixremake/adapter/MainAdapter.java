package com.kao.netflixremake.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kao.netflixremake.R;
import com.kao.netflixremake.model.Category;
import com.kao.netflixremake.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CategoryHolder>{

    private List<Category> categories;
    private Context context;

    public MainAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categories.get(position);
        try {
            holder.textView.setText(category.getName());
            holder.recyclerView.setAdapter( new MovieAdapter(category.getMovies(), R.layout.movie_item));
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private RecyclerView recyclerView;

        public CategoryHolder(View view) {
            super(view);

            textView = view.findViewById(R.id.txt_view_title);
            recyclerView =view.findViewById(R.id.recycler_view_movie);
        }

    }
}

