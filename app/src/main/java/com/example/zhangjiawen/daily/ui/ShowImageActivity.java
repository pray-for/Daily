package com.example.zhangjiawen.daily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhangjiawen.daily.R;
import com.example.zhangjiawen.daily.cache.ACache;
import com.example.zhangjiawen.daily.task.DownloadImageTask;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by zhangjiawen on 2017/5/6.
 */
public class ShowImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private PhotoViewAttacher mAttacher;
    private ACache aCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        aCache = ACache.get(this);
        imageView = (ImageView) findViewById(R.id.show_image);
        mAttacher = new PhotoViewAttacher(imageView);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this , "获取图片失败" , Toast.LENGTH_SHORT).show();
        } else {
            if (aCache.getAsBitmap(url) != null){
                imageView.setImageBitmap(aCache.getAsBitmap(url));
            } else {
                new DownloadImageTask(imageView).execute(new String[]{url});
            }
            mAttacher.update();
        }
    }
}
