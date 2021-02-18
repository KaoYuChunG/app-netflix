package com.kao.netflixremake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kao.netflixremake.adapter.MovieAdapter;
import com.kao.netflixremake.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    TextView textTitle;
    TextView textDesc;
    TextView textCast;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        textTitle = findViewById(R.id.txt_view_title);
        textDesc = findViewById(R.id.txt_view_desc);
        textCast = findViewById(R.id.txt_view_cast);

        recyclerView = findViewById(R.id.recycler_view_similar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add icon de voltar no toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle(null);
        }

        //usa ContextCompat para compatilha com version mais antigo
        LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.shadows);

        if(drawable != null) {
            //aqui que troca imagem
            Drawable movewCover =  ContextCompat.getDrawable(this, R.drawable.movie_4);
            //relaciona layout com item no layer-list
            drawable.setDrawableByLayerId(R.id.cover_drawable, movewCover);
            //conecta ao layout para show na tela
            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(drawable);
        }

        textTitle.setText("Batman");
        textDesc.setText("Batman é um filme de super-heróis norte-americano de 1989 e baseado no personagem homônimo da DC Comics. Dirigido por Tim Burton, é o primeiro longa-metragem da série de filmes inicial do Batman da Warner Bros. O filme é estrelado por Michael Keaton como Bruce Wayne/Batman, com Jack Nicholson, Kim Basinger, Robert Wuhl, Pat Hingle, Billy Dee Williams, Michael Gough e Jack Palance. No filme, Batman lida com a ascensão de um gênio do crime conhecido como \"O Coringa\".");
        textCast.setText(getString(R.string.cast, "Cristiane sale" + "Michel lima"));

        List<Movie> movies = new ArrayList<>();
        for (int i =0 ; i < 30 ; i++) {
            Movie movie = new Movie();
            movies.add(movie);
        }

        recyclerView.setAdapter(new MovieAdapter(movies, R.layout.movie_item_similar));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}