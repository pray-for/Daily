package com.example.zhangjiawen.daily.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangjiawen.daily.R;
import com.example.zhangjiawen.daily.bean.LatestNewsEntity;
import com.example.zhangjiawen.daily.cache.ACache;
import com.example.zhangjiawen.daily.support.MyApplication;
import com.example.zhangjiawen.daily.task.DownloadImageTask;
import com.example.zhangjiawen.daily.ui.NewsActivity;

import java.util.List;

/**
 * Created by zhangjiawen on 2017/5/1.
 */
public class ViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private List<LatestNewsEntity.TopStoriesEntity> topStoriesEntities;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapter(){
        this.layoutInflater = LayoutInflater.from(MyApplication.getContext());
    }

    public List<LatestNewsEntity.TopStoriesEntity> getTopStoriesEntities(){
        return topStoriesEntities;
    }

    public void setTopStoriesEntities(List<LatestNewsEntity.TopStoriesEntity> topStoriesEntities) {
        this.topStoriesEntities = topStoriesEntities;
    }

    //当item被销毁时
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view_item = layoutInflater.inflate(R.layout.viewpager_item , container , false);
        TextView title = (TextView) view_item.findViewById(R.id.topStoryTitle);
        title.setText(topStoriesEntities.get(position).getTitle());
        view_item.setTag(topStoriesEntities.get(position).getId());
        ImageView imageView = (ImageView) view_item.findViewById(R.id.imageview);
        imageView.setImageResource(R.drawable.image_start);
        //如果当前内存缓存中存在这张图片，则从文件缓存中取出，否则从硬盘缓存中取出，如果硬盘缓存中没有，则进行网络请求
        //从内存缓存中取出图片
        Bitmap bitmap = MyApplication.getLruCache().get(topStoriesEntities.get(position).getImage());
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            //从硬盘缓存中取出图片
            bitmap = ACache.get(MyApplication.getContext()).getAsBitmap(topStoriesEntities.get(position).getImage());
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                //从网络请求获取图片
                new DownloadImageTask(imageView).execute(topStoriesEntities.get(position).getImage());
            }
        }
        container.addView(view_item);
        //给每个view_item设置事件监听器
        view_item.setOnClickListener(this);
        return view_item;
    }

    @Override
    public int getCount() {
        return topStoriesEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 重写监听方法，点击跳转activity并将id通过intent传递到新的activity
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MyApplication.getContext() , NewsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id" , (Integer) view.getTag());
        MyApplication.getContext().startActivity(intent);
    }

}
