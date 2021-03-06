package com.example.zhangjiawen.daily.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zhangjiawen.daily.R;
import com.example.zhangjiawen.daily.adapter.RecyclerViewAdapter;
import com.example.zhangjiawen.daily.bean.LatestNewsEntity;
import com.example.zhangjiawen.daily.cache.ACache;
import com.example.zhangjiawen.daily.conn.ZhiHuDailyApi;
import com.example.zhangjiawen.daily.task.ClearCacheTask;
import com.example.zhangjiawen.daily.task.LatestNewsTask;
import com.example.zhangjiawen.daily.task.SetNewsTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //缓存类
    private ACache aCache;
    //recyclerView适配器
    private RecyclerViewAdapter recyclerViewAdapter;
    //下拉刷新控件
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    //存放新闻实体类的集合
    private List<LatestNewsEntity> latestNewsEntities;
    //记录第一次按退出按钮的时间
    private long exitTime;
    //是否正在进行触底加载
    public static boolean onLoading = false;
    //创建存储类
    private SharedPreferences sharedPreferences;
    //默认加载的Url
    private String[] params = new String[]{ZhiHuDailyApi.latest_news};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("isNightMode" , Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("mode" , false)) {
            setTheme(R.style.MyTheme_Night);
        } else {
            setTheme(R.style.MyTheme_Day);
        }
        setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(toolbar);
        //启动程序先读取缓存数据进行加载，再从网络请求
        if (aCache.getAsObject(ZhiHuDailyApi.latest_news) != null){
            LatestNewsEntity latestNewsEntity = (LatestNewsEntity) aCache.getAsObject(params[0]);
            latestNewsEntities.add(latestNewsEntity);
            new SetNewsTask(swipeRefreshLayout , recyclerView , recyclerViewAdapter , latestNewsEntities).execute();
        }
        new LatestNewsTask(swipeRefreshLayout , recyclerView , recyclerViewAdapter , latestNewsEntities).execute(params);
    }

    /**
     * 初始化控件
     */
    private void initView(){
        aCache = ACache.get(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        latestNewsEntities = new ArrayList<LatestNewsEntity>();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipeRefreshLayout);
        //配置下拉事件监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新时清空集合
                latestNewsEntities = new ArrayList<LatestNewsEntity>();
                new LatestNewsTask(swipeRefreshLayout , recyclerView , recyclerViewAdapter , latestNewsEntities).execute(params);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //配置recyclerView滚动事件监听器，以实现触底加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //当前可见的item数量
                int childCount = linearLayoutManager.getChildCount();
                //已经滚动过的item数量
                int firstVisableItem = linearLayoutManager.getItemCount();
                //item总数量
                int itemCount = linearLayoutManager.getItemCount();
                if (!onLoading){
                    //如果 当前可见的item数量+已经滚动过的item数量 >= item总数量，则表示已经加载到底部
                    if ((childCount + firstVisableItem) >= itemCount){
                        onLoading = true;
                        String date = latestNewsEntities.get(latestNewsEntities.size()-1).getDate();
                        String[] params_url = new String[]{ZhiHuDailyApi.before_news + date};
                        new LatestNewsTask(swipeRefreshLayout , recyclerView , recyclerViewAdapter , latestNewsEntities).execute(params_url);
                    }
                }
            }
        });
        recyclerViewAdapter = new RecyclerViewAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            //点击清除缓存
            case R.id.clear_cache:
                //启用一个异步任务，清除缓存
                new ClearCacheTask(aCache).execute();
                break;
            //点击夜间/日间模式
            case R.id.night_mode:
                //调用saveDayNightMode方法，将当前所处模式存入sharePreferences
                saveDayNightMode(!sharedPreferences.getBoolean("mode" , false));
                //重新创建activity
                recreate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 将当前处于日间模式/夜间模式 存入sharePreferences
     * @param flag
     */
    private void saveDayNightMode(boolean flag){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("mode" , flag);
        editor.commit();
    }

    /**
     * 实现点击两次返回后退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis() - exitTime) > 2000){
                Toast.makeText(this , "再按一次退出" , Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else {
                onBackPressed();
            }
        }
        return false;
    }
}
