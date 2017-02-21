package com.library.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.library.utils.Utils;

/**
 * Created by Administrator on 2017/2/21.
 */

public class DrawableHelper {
    /**
     * 获取Selector
     * @param normalDraw
     * @param pressedDraw
     * @return
     */
    public static StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable  = new StateListDrawable();
        stateListDrawable.addState(new int[]{ android.R.attr.state_pressed }, pressedDraw);
        stateListDrawable.addState(new int[]{ }, normalDraw);
        return stateListDrawable ;
    }
    //圆形背景
    public static class RoundBackgroundBuilder{
        private Context mContext;
        private GradientDrawable gradientDrawable;
        public RoundBackgroundBuilder(Context context){
            mContext = context;
            gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.OVAL);
            gradientDrawable.setUseLevel(false);
        }
        //设置宽高
        public RoundBackgroundBuilder setSize(int width, int height){
            gradientDrawable.setSize(width,height);
            return this;
        }
        //背景颜色
        public RoundBackgroundBuilder setSolidColor(int solidColor){
            gradientDrawable.setColor(solidColor); //背景色
            return this;
        }
        //边框
        public RoundBackgroundBuilder setStroke(int strokeWidth, int strokeColor){
            strokeWidth = Utils.Int2dp(mContext,strokeWidth);
            //strokeColor = ContextCompat.getColor(mContext,strokeColor);
            gradientDrawable.setStroke(strokeWidth,strokeColor); //边框宽度，边框颜色
            return this;
        }
        public GradientDrawable create(){
            return gradientDrawable;
        }
    }
}
