package com.example.zhangjiawen.daily.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.zhangjiawen.daily.support.Http;

import java.io.IOException;

/**
 * Created by zhangjiawen on 2017/5/3.
 */
public class DownloadImageTask extends AsyncTask<String , Void , Bitmap> {

    private ImageView imageView;
    private String[] image_url;

    public DownloadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        for (int i = 0; i < image_url.length; i++){

        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        image_url = params;
        Bitmap bitmap = null;
        for (int i = 0; i < params.length; i++){
            try{
                bitmap = Http.getImage(params[i]);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
