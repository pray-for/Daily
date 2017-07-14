package com.example.zhangjiawen.daily.task;

import android.os.Handler;
import android.os.Message;

import com.example.zhangjiawen.daily.ui.TopItemHolder;

/**
 * Created by zhangjiawen on 2017/5/6.
 */
public class ViewPagerThread implements Runnable {

    //头部新闻对象
    private TopItemHolder topItemHolder;
    //ViewPager的item总数
    private int itemCount;
    //当前选择的item
    private int currentItem;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            topItemHolder.getViewPager().setCurrentItem(currentItem);
        }
    };

    public ViewPagerThread(TopItemHolder topItemHolder , int itemCount){
        this.topItemHolder = topItemHolder;
        this.itemCount = itemCount;
        //当前选择的item
        this.currentItem = topItemHolder.getDotsRadioGroup().getmPosition();
    }

    @Override
    public void run() {
        //获取当前选择的item
        this.currentItem = topItemHolder.getDotsRadioGroup().getmPosition();
        currentItem = (currentItem + 1) % itemCount;
        handler.sendEmptyMessage(0);
    }
}
