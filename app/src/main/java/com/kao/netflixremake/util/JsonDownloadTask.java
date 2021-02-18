package com.kao.netflixremake.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kao.netflixremake.model.Category;
import com.kao.netflixremake.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class JsonDownloadTask extends AsyncTask<String , Void, List<Category>> {

    private final Context context;
    ProgressDialog dialog;

    public JsonDownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Carregando", "", true);
    }

    @Override
    protected List<Category> doInBackground(String... params) {
        String url = params[0];

        try {
            URL requestUrl = new URL(url);

            HttpsURLConnection urlConnection = (HttpsURLConnection) requestUrl.openConnection();
            urlConnection.setReadTimeout(2000);
            urlConnection.setConnectTimeout(2000);

            int responseCode = urlConnection.getResponseCode();
            if(responseCode > 400) {
                throw new IOException("Erro na comunicação do servidor");
            }

            InputStream inputStream = urlConnection.getInputStream();

            BufferedInputStream in = new BufferedInputStream(inputStream);

            String jsonArray = toString(in);
            Log.i("TESTE", jsonArray);

            List<Category> categories = getCategories(new JSONObject(jsonArray));
            in.close();

            return categories;

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Category> getCategories(JSONObject json) throws JSONException {
        List<Category> categories = new ArrayList<>();

        JSONArray categotyArray = json.getJSONArray("category");
        for(int i = 0 ; i < categotyArray.length(); i++) {
            JSONObject category = categotyArray.getJSONObject(i);
            String title = category.getString("title");

            List<Movie> movies = new ArrayList<>();
            JSONArray movieArray = json.getJSONArray("movie");
            for(int j = 0 ; j < movieArray.length(); j++) {
                JSONObject movie = movieArray.getJSONObject(i);
                String coverUrl = movie.getString("cover_url");

                Movie movieObj = new Movie();
                movieObj.setCoverUrl(coverUrl);
                movies.add(movieObj);
            }

            Category categoryObj = new Category();
            categoryObj.setName(title);
            categoryObj.setMovies(movies);
            categories.add(categoryObj);
        }

        return categories;
    }

    private String toString(BufferedInputStream is) throws IOException {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        int lidos;
        while((lidos = is.read(bytes)) > 0 ) {
            boas.write(bytes, 0,lidos);
        }
        return new String(boas.toByteArray());
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);
        dialog.dismiss();
    }
}
