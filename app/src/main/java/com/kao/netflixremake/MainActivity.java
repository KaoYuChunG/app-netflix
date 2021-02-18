package com.kao.netflixremake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kao.netflixremake.adapter.MainAdapter;
import com.kao.netflixremake.model.Category;
import com.kao.netflixremake.model.Movie;
import com.kao.netflixremake.util.JsonDownloadTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);


        //monta listas de category e movies
        List<Category> categories = new ArrayList<>();
        for (int i =0 ; i < 30 ; i++) {
            Category category = new Category();
            category.setName("cat" + i);

            List<Movie> movies = new ArrayList<>();
            for (int j =0 ; j < 30 ; j++) {
                Movie movie = new Movie();
//                movie.setCoverUrl(R.drawable.movie);
                movies.add(movie);
            }

            category.setMovies(movies);
            categories.add(category);
        }
        MainAdapter adapter = new MainAdapter(categories, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        new JsonDownloadTask(this).execute("https://tiagoaguiar.co/api/netflix/home");
    }


}