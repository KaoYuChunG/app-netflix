package com.kao.netflixremake.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.kao.netflixremake.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.core.content.ContextCompat;

public class ImageDownloadTask extends AsyncTask<String , Void, Bitmap> {

    private final WeakReference<ImageView> imageWeakReferenc;
    private boolean shadowEnabled;

    public ImageDownloadTask(ImageView image) {
        this.imageWeakReferenc = new  WeakReference<ImageView>(image);
    }

    public void setShadowEnabled(boolean shadowEnabled) {
        this.shadowEnabled = shadowEnabled;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imageUrl = params[0];
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(imageUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();
            if(responseCode != 200) {
                return  null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if(inputStream != null) {
                return BitmapFactory.decodeStream(inputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled()) {
          bitmap = null;
        }

        ImageView imageView = imageWeakReferenc.get();
        if(imageView != null && bitmap != null) {
            if (shadowEnabled) {
                LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(imageView.getContext(),
                        R.drawable.shadows);
                if (drawable != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                    drawable.setDrawableByLayerId(R.id.cover_drawable, bitmapDrawable);
                    imageView.setImageDrawable(drawable);
                }

            } else {
                //tratamento para imagem
                if(bitmap.getWidth() < imageView.getWidth() || bitmap.getHeight() < imageView.getHeight()) {
                    Matrix matrix = new Matrix();
                    //no caso escala imagem
                    matrix.postScale((float)imageView.getWidth() / (float)bitmap.getWidth(),
                            (float)imageView.getHeight() / (float)bitmap.getHeight());
                    //aqui pode aplicar filtro, mas nao é o caso
                    bitmap = Bitmap.createBitmap(bitmap, 0,0 ,bitmap.getWidth(), bitmap.getHeight(), matrix , false);
                }
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
