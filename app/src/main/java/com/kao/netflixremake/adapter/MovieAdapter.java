package com.kao.netflixremake.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kao.netflixremake.MovieActivity;
import com.kao.netflixremake.R;
import com.kao.netflixremake.model.Movie;
import com.kao.netflixremake.util.ImageDownloadTask;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> implements OnItemClickListener {

    private List<Movie> movies;
    private int layout;

    public MovieAdapter(List<Movie> movies, int layout) {
        this.movies = movies;
        this.layout = layout;
    }

    public void setMovies(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
    }

    @Override
    public void onClick(int position, final Context context, ImageView image) {
        int id = movies.get(position).getId();
        Bundle b = ActivityOptionsCompat
                .makeSceneTransitionAnimation((Activity) context, image , "image_view_cover").toBundle();
        if ( id <= 3) {
            Intent intent = new Intent(context, MovieActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent,b);
        }
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        //segundo parametro vai relaciona  onClick(int position)
        return new MovieHolder(view, this, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = movies.get(position);
        new ImageDownloadTask(holder.imageView).execute(movie.getCoverUrl());
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }


    public static class MovieHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public MovieHolder (View view, final OnItemClickListener onItemClickListener, final Context context) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.image_view_cover);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //identifica qual item foi clicado
                    onItemClickListener.onClick(getAdapterPosition(), context, imageView);
                }
            });
        }

    }
}
