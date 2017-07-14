package com.example.zhangjiawen.daily.task;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.zhangjiawen.daily.adapter.RecyclerViewAdapter;
import com.example.zhangjiawen.daily.bean.LatestNewsEntity;
import com.example.zhangjiawen.daily.support.Http;
import com.example.zhangjiawen.daily.support.MyApplication;
import com.example.zhangjiawen.daily.support.Tool;
import com.example.zhangjiawen.daily.ui.MainActivity;

import java.util.List;

/**
 * Created by zhangjiawen on 2017/5/2.
 * 将Json数据解析为实体类的异步任务
 */
public class LatestNewsTask extends AsyncTask<String , Void , LatestNewsEntity> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<LatestNewsEntity> latestNewsEntities;
    private String url;

    public LatestNewsTask(SwipeRefreshLayout swipeRefreshLayout , RecyclerView recyclerView ,
                          RecyclerViewAdapter recyclerViewAdapter , List<LatestNewsEntity> latestNewsEntities){
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.latestNewsEntities = latestNewsEntities;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //在进行非触底加载时展示下拉刷新动画
        if (!MainActivity.onLoading) {
            this.swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    protected void onPostExecute(LatestNewsEntity latestNewsEntity) {
        super.onPostExecute(latestNewsEntity);
        if (latestNewsEntity == null) {
            Toast.makeText(MyApplication.getContext() , "获取数据失败" , Toast.LENGTH_SHORT).show();
            this.swipeRefreshLayout.setRefreshing(false);
            return;
        }
        //移除重复项
        for (int i = 0 ; i < latestNewsEntities.size() ; i++){
            if (latestNewsEntities.get(i).getDate().equals(latestNewsEntity.getDate())) {
                latestNewsEntities.remove(i);
                i--;
            }
        }
        latestNewsEntities.add(latestNewsEntity);
        //启动异步任务，将数据更新到UI
        new SetNewsTask(swipeRefreshLayout , recyclerView , recyclerViewAdapter , latestNewsEntities).execute();
        //启动异步任务，将数据写入缓存
        new SaveToCacheTask(url , latestNewsEntity).execute();
    }



    @Override
    protected LatestNewsEntity doInBackground(String... params) {
        LatestNewsEntity latestNewsEntity = null;
        //如果没有网络连接，则返回null
        if (!Tool.isNetConnected()){
            return null;
        }
        for (int i = 0; i < params.length; i++){
            url = params[i];
            try {
                String json_latestNewsEntity = Http.getJsonString(params[i]);
                if (json_latestNewsEntity == null){
                    return null;
                }
                latestNewsEntity = JSON.parseObject(json_latestNewsEntity,LatestNewsEntity.class);
                for (int j = 0; j < latestNewsEntity.getStories().size(); j++){
                    latestNewsEntity.getStories().get(j).setDate(latestNewsEntity.getDate());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return latestNewsEntity;
    }
}
