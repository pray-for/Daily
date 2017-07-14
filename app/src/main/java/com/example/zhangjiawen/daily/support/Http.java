package com.example.zhangjiawen.daily.support;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhangjiawen on 2017/5/3.
 */
public class Http {
    /**
     * 从网络上获取Json数据的字符串
     * @param urlAddr
     * @return
     * @throws IOException
     */
    public static String getJsonString(String urlAddr) throws IOException{
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        if (Tool.isNetConnected()) {
            try {
                url = new URL(urlAddr);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] date = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(date)) != -1){
                        byteArrayOutputStream.write(date , 0 , len);
                    }
                    return new String(byteArrayOutputStream.toByteArray());
                } else {
                    throw new IOException("Network Error" + httpURLConnection.getResponseCode());
                }
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 从网络上获取一张Bitmap图片
     * @param urlAddr
     * @return
     * @throws IOException
     */
    public static Bitmap getImage(String urlAddr) throws IOException{
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        if (Tool.isNetConnected()){
            try {
                url = new URL(urlAddr);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] date = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(date)) != -1){
                        byteArrayOutputStream.write(date , 0 , len);
                    }
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray() ,
                            0 , byteArrayOutputStream.size());
                    return bitmap;
                } else {
                    throw new IOException("Network Error" + httpURLConnection.getResponseCode());
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
