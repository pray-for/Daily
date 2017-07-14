package com.example.zhangjiawen.daily.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.zhangjiawen.daily.bean.LatestNewsEntity;
import com.example.zhangjiawen.daily.bean.NewsEntity;
import com.example.zhangjiawen.daily.cache.ACache;
import com.example.zhangjiawen.daily.support.MyApplication;

/**
 * Created by zhangjiawen on 2017/5/3.
 * 将数据写到缓存
 */
public class SaveToCacheTask extends AsyncTask<Void , Void , Void> {

    private LatestNewsEntity latestNewsEntity;
    private String url;
    private String images_url;
    private Bitmap bitmap;
    private NewsEntity newsEntity;
    private ACache aCache;

    public SaveToCacheTask(){
        aCache = ACache.get(MyApplication.getContext());
    }

    public SaveToCacheTask(String url , NewsEntity newsEntity){
        this();
        this.url = url;
        this.newsEntity = newsEntity;
    }

    public SaveToCacheTask(String url , LatestNewsEntity latestNewsEntity){
        this();
        this.url = url;
        this.latestNewsEntity = latestNewsEntity;
    }

    public SaveToCacheTask(String images_url , Bitmap bitmap){
        this();
        this.images_url = images_url;
        this.bitmap = bitmap;
    }

    public SaveToCacheTask(String url , LatestNewsEntity latestNewsEntity ,
                           String images_url , Bitmap bitmap){
        this();
        this.url = url;
        this.latestNewsEntity = latestNewsEntity;
        this.images_url = images_url;
        this.bitmap = bitmap;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (latestNewsEntity != null) {
            saveLatestNewsEntityToCache();
        }
        if (newsEntity != null){
            saveNewsEntityToCache();
        }
        if (bitmap != null){
            saveBitmapToCache();
            //如果内存缓存中不存在这张图片，就把图片写入内存缓存
            if (MyApplication.getLruCache().get(images_url) == null){
                saveBitmapToLruCache();
            }
        }
        return null;
    }

    /**
     * 将一个可序列化的对象LatestNewsEntity存入缓存
     */
    private void saveLatestNewsEntityToCache(){
        aCache.put(url , latestNewsEntity);
    }

    /**
     * 将一个可序列化的对象NewsEntity存入缓存
     */
    private void saveNewsEntityToCache(){
        aCache.put(url , newsEntity);
    }

    /**
     * 将一个bitmap图片存入缓存
     */
    private void saveBitmapToCache(){
        aCache.put(images_url , bitmap);
    }

    /**
     * 将一张图片写入内存缓存
     */
    private void saveBitmapToLruCache(){
        MyApplication.getLruCache().put(images_url , bitmap);
    }

}
