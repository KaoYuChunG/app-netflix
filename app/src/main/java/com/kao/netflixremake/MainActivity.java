package com.kao.netflixremake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kao.netflixremake.adapter.MainAdapter;
import com.kao.netflixremake.model.Category;
import com.kao.netflixremake.util.CategoryTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  CategoryTask.CategoryLoader{
    private static final String LOG_TAG = "MainActivity";
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);

        adapter = new MainAdapter(new ArrayList<Category>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        CategoryTask categoryTask = new CategoryTask(this);
        //esse que faz relacao ao mainActivity
        categoryTask.setCategoryLoader(this);
        categoryTask.execute("https://tiagoaguiar.co/api/netflix/home");
    }


    @Override
    public void onResult(List<Category> categories) {
        //envolve tres partes, categoryTask , mainActivity, MainAdapter
        adapter.setCategories(categories);
        adapter.notifyDataSetChanged();
    }
}