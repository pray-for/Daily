package com.example.zhangjiawen.daily.support;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by zhangjiawen on 2017/5/2.
 */
public class MyApplication extends Application
{
    //全局对象
    private static Context context;
    //内存缓存对象，缓存bitmap图片
    private static LruCache<String , Bitmap> lruCache;
    //内存缓存的大小
    private final static int CACHESIZE = 4 * 1024 * 1024;//4MB

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //实例化内存缓存对象
        lruCache = new LruCache<String , Bitmap>(CACHESIZE){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 全局获得context对象的方法
     * @return context对象
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 全局调用内存缓存类对象
     * @return lruCache对象
     */
    public static LruCache<String, Bitmap> getLruCache() {
        return lruCache;
    }
}
