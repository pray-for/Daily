package com.example.zhangjiawen.daily.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhangjiawen.daily.R;

/**
 * Created by zhangjiawen on 2017/5/3.
 */
public class DotsRadioGroup extends RadioGroup implements ViewPager.OnPageChangeListener {

    //上下文
    private Context mContext;
    //关联的ViewPager
    private ViewPager mVPContent;
    //当前显示的指示点
    private int mPosition;
    //指示点集合
    private RadioButton[] mDotsButton;

    /**
     * 构造方法
     * @param context
     */
    public DotsRadioGroup(Context context) {
        super(context);
    }

    public DotsRadioGroup(Context context , AttributeSet attrs){
        super(context , attrs);
        //将上下文赋值给当前类
        this.mContext = context;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        if (mDotsButton != null) {
            for (int i = 0; i < mDotsButton.length; i++){
                boolean isChecked = i == mPosition ? true : false;
                mDotsButton[i].setChecked(isChecked);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 获取该位置
     * @return
     */
    public int getmPosition() {
        return mPosition;
    }

    public void setDotView(ViewPager viewPager , int pageCount){
        if (pageCount < 2) {
            setVisibility(View.GONE);
            return;
        }
        //清理所有的点
        setVisibility(View.VISIBLE);
        removeAllViews();
        mDotsButton = new RadioButton[pageCount];
        this.mVPContent = viewPager;
        mVPContent.addOnPageChangeListener(this);
        //设置属性
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT ,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(10 , 0 , 10 , 0);
        params.gravity = Gravity.CENTER;

        RadioButton radioButton = null;
        for (int i = 0; i < pageCount; i++){
            radioButton = new RadioButton(mContext);
            radioButton.setButtonDrawable(R.drawable.selector_dots);
            radioButton.setLayoutParams(params);
            radioButton.setClickable(false);
            addView(radioButton , params);
            mDotsButton[i] = radioButton;
        }
        //第一个默认选中
        mDotsButton[0].setChecked(true);


    }
}
