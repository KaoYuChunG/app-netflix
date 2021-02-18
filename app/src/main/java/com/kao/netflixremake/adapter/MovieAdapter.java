package com.kao.netflixremake.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kao.netflixremake.R;
import com.kao.netflixremake.model.Category;
import com.kao.netflixremake.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>{

    private final List<Movie> movies;
    private int layout;

    public MovieAdapter(List<Movie> movies, int layout) {
        this.movies = movies;
        this.layout = layout;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = movies.get(position);
//        holder.imageView.setImageResource(movie.getCoverUrl());
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public MovieHolder (View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.image_view_cover);
        }

    }
}
