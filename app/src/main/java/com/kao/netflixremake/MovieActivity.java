package com.kao.netflixremake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.kao.netflixremake.adapter.MovieAdapter;
import com.kao.netflixremake.model.Movie;
import com.kao.netflixremake.model.MovieDetail;
import com.kao.netflixremake.util.ImageDownloadTask;
import com.kao.netflixremake.util.MovieDetailTask;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements MovieDetailTask.MovieDetailLoader {

    TextView textTitle;
    TextView textDesc;
    TextView textCast;
    RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ImageView imgCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        textTitle = findViewById(R.id.txt_view_title);
        textDesc = findViewById(R.id.txt_view_desc);
        textCast = findViewById(R.id.txt_view_cast);
        recyclerView = findViewById(R.id.recycler_view_similar);
        imgCover = findViewById(R.id.image_view_cover);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add icon de voltar no toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle(null);
        }


        //usa ContextCompat para compatilha com version mais antigo
//        LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.shadows);
// mocaking data
//        if(drawable != null) {
            //aqui que troca imagem
//            Drawable movewCover =  ContextCompat.getDrawable(this, R.drawable.movie_4);
            //relaciona layout com item no layer-list
//            drawable.setDrawableByLayerId(R.id.cover_drawable, movewCover);
            //conecta ao layout para show na tela
//            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(drawable);
//        }

        List<Movie> movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(movies, R.layout.movie_item_similar);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("id");
            MovieDetailTask movieDetailTask = new MovieDetailTask(this);
            //passa this para relaciona ao onResult
            movieDetailTask.getMovieDetailLoader(this);
            movieDetailTask.execute("https://tiagoaguiar.co/api/netflix/" + id);
        }

    }

    //seta para anterior
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            //se for true mata(destruir) activity atual
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResult(MovieDetail movieDetail) {

        Log.i("TESTE", movieDetail.toString());
        textTitle.setText(movieDetail.getMovie().getTitle());
        textDesc.setText(movieDetail.getMovie().getDesc());
        textCast.setText(movieDetail.getMovie().getCast());

        ImageDownloadTask imageDownloaderTask = new ImageDownloadTask(imgCover);
        imageDownloaderTask.setShadowEnabled(true);
        imageDownloaderTask.execute(movieDetail.getMovie().getCoverUrl());

        movieAdapter.setMovies(movieDetail.getMovieSimilar());
        movieAdapter.notifyDataSetChanged();
    }
}