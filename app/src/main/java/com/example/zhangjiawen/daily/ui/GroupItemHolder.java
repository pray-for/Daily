package com.example.zhangjiawen.daily.ui;

import android.view.View;
import android.widget.TextView;

import com.example.zhangjiawen.daily.R;

/**
 * Created by zhangjiawen on 2017/5/4.
 */
public class GroupItemHolder extends SimpleItemHolder{

    //头部时间显示
    private TextView textView_time;

    public GroupItemHolder(View itemView) {
        super(itemView);
        textView_time = (TextView) itemView.findViewById(R.id.textview_time);
    }

    public TextView getTextView_time() {
        return textView_time;
    }
}
