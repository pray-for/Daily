package com.example.zhangjiawen.daily.task;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.zhangjiawen.daily.adapter.RecyclerViewAdapter;
import com.example.zhangjiawen.daily.bean.LatestNewsEntity;
import com.example.zhangjiawen.daily.ui.MainActivity;

import java.util.List;

/**
 * Created by zhangjiawen on 2017/5/2.
 */
public class SetNewsTask extends AsyncTask<Void , Void , Void>{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<LatestNewsEntity> latestNewsEntities;

    public SetNewsTask(SwipeRefreshLayout swipeRefreshLayout , RecyclerView recyclerView ,
                       RecyclerViewAdapter recyclerViewAdapter , List<LatestNewsEntity> latestNewsEntities){
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.latestNewsEntities = latestNewsEntities;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        recyclerViewAdapter.setLatestNewsEntities(latestNewsEntities);
        if (recyclerView.getAdapter() == null){
            recyclerView.setAdapter(recyclerViewAdapter);
        }
        recyclerViewAdapter.notifyDataSetChanged();
        this.swipeRefreshLayout.setRefreshing(false);
        //加载完成，将触底加载设置为false
        MainActivity.onLoading = false;

    }
}
