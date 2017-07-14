package com.example.zhangjiawen.daily.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zhangjiawen on 2017/5/3.
 * 工具类
 */
public class Tool {
    /**
     * 检测当前的网络连接状态
     * @return
     */
    public static boolean isNetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()){
                //当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED){
                    //当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
