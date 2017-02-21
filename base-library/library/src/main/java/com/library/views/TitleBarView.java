package com.library.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.R;
import com.library.helper.DrawableHelper;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 标题栏
 * Created by Administrator on 2017/2/21.
 */
public class TitleBarView extends RelativeLayout{

    private AppCompatActivity mActivity;
    //处理状态背景
    private SystemBarTintManager mTintManager;
    //左边区域
    private LinearLayout mLeftLayout;
    //右边区域
    private LinearLayout mRightLayout;
    //中间区域
    private LinearLayout mCoreLayout;
    //标题
    private TextView mTitle;
    //标题颜色
    private int mTitleTextColor;
    //标题文字大小
    private int mTitleTextSize;
    //副标题
    private TextView mSubtitle;
    //副标题色
    private int mSubtitleTextSize;
    //副标题文字大小
    private int mSubtitleTextColor;
    //左边Logo
    private ImageView mLeftLogoView;
    //右边Logo
    private ImageView mRightLogoView;
    //Logo描述
    private TextView mLogoDescription;
    //固定图片大小
    private int mLogoSize;
    //Logo按下时的背景颜色
    private int mLogoPressedBackgroundColor;

    public TitleBarView(Context context) {
        this(context,null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.titleBarAttr);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if(this.isInEditMode())return;
        if(context instanceof AppCompatActivity){
            mActivity = (AppCompatActivity)context;
        }

        initWindow();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView, defStyleAttr, R.style.defaultTitleBarStyle);
        int barHeight = a.getDimensionPixelSize(R.styleable.TitleBarView_barHeight,0);
        if(barHeight != 0){
            this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,barHeight));
        }
        int barBackgroundColor = a.getColor(R.styleable.TitleBarView_barBackground,0);
        if(barBackgroundColor !=0){
            this.setBackgroundColor(barBackgroundColor);
        }
        int statusBarColor = a.getColor(R.styleable.TitleBarView_statusBarColor,0);
        if(statusBarColor != 0){
            setStatusBarTintColor(statusBarColor);
        }
        int functionBarColor = a.getColor(R.styleable.TitleBarView_functionBarColor,0);
        if(functionBarColor !=0){
            setFunctionBarTintColor(functionBarColor);
        }
        mTitleTextColor = a.getColor(R.styleable.TitleBarView_barTitleTextColor,mTitleTextColor);
        mTitleTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_barTitleTextSize,mTitleTextSize);
        mSubtitleTextColor = a.getColor(R.styleable.TitleBarView_barSubtitleTextColor,mSubtitleTextColor);
        mSubtitleTextSize = a.getDimensionPixelSize(R.styleable.TitleBarView_barSubtitleTextSize,mSubtitleTextSize);
        mLogoPressedBackgroundColor = a.getColor(R.styleable.TitleBarView_logoPressedBackgroundColor,mLogoPressedBackgroundColor);
        mLogoSize = a.getDimensionPixelSize(R.styleable.TitleBarView_logoSize,mLogoSize);
        a.recycle();
        initLayout();
    }
    //初始化布局
    private void initLayout() {
        mCoreLayout = new LinearLayout(getContext());
        mCoreLayout.setGravity(Gravity.CENTER);
        mCoreLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams mCoreLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        mCoreLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(mCoreLayout,mCoreLayoutParams);

        mLeftLayout = new LinearLayout(getContext());
        mLeftLayout.setPadding(30,0,0,0);
        mLeftLayout.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams mLeftLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        this.addView(mLeftLayout,mLeftLayoutParams);

        mRightLayout = new LinearLayout(getContext());
        mRightLayout.setGravity(Gravity.CENTER);
        mRightLayout.setPadding(0,0,30,0);
        RelativeLayout.LayoutParams mRightLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.addView(mRightLayout,mRightLayoutParams);
    }
    //设置图标
    public void setRightLogo(int resId) {
        setRightLogo(ContextCompat.getDrawable(getContext(), resId));
    }
    //设置图标
    public void setRightLogo(Drawable drawable){
        if(drawable != null){
            ensureRightLogoView();
            mRightLogoView.setImageDrawable(drawable);
            mRightLogoView.setVisibility(VISIBLE);

        }else {
            mRightLayout.removeAllViews();
            mRightLayout.setOnClickListener(null);
        }
    }
    //设置图标点击事件
    public void setRightLogoOnClickListener(OnClickListener listener){
        mRightLogoView.setOnClickListener(listener);
    }
    //确保右边LogoView被实例化
    private void ensureRightLogoView() {
        mRightLayout.removeAllViews();
        mRightLayout.setOrientation(LinearLayout.HORIZONTAL);
        if(mRightLogoView == null){
            mRightLogoView = new ImageView(getContext());
            mRightLogoView.setLayoutParams(new LayoutParams(
                    mLogoSize,
                    mLogoSize
            ));
            mRightLogoView.setBackground(getLogoBackground());
            mRightLogoView.setVisibility(GONE);
        }
        mRightLogoView.setOnClickListener(null);
        mRightLayout.addView(mRightLogoView);
    }
    public void setLeftLogo(int resId) {
        setLeftLogo(ContextCompat.getDrawable(getContext(), resId));
    }
    //设置图标
    public void setLeftLogo(Drawable drawable){
        if(drawable != null){
            ensureLeftLogoView();
            mLeftLogoView.setImageDrawable(drawable);
            mLeftLogoView.setVisibility(VISIBLE);
        }else {
            mLeftLayout.removeAllViews();
            mLeftLayout.setOnClickListener(null);
            mLeftLogoView = null;
            mLogoDescription = null;
        }
    }
    //设置图标点击事件
    public void setLeftLogoOnClickListener(OnClickListener listener){
        mLeftLogoView.setOnClickListener(listener);
    }
    //设置图标描述
    public void setLogoDescription(int resId) {
        setLogoDescription(getContext().getText(resId));
    }
    //设置图标描述
    public void setLogoDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureLeftLogoView();
            mLogoDescription.setText(description);
            mLogoDescription.setVisibility(VISIBLE);
        }
    }
    //设置透明度
    public void setAlpha(int alpha){
        this.getBackground().setAlpha(alpha);
        if(mLeftLogoView != null){
            mLeftLogoView.getBackground().setAlpha(alpha);
        }
        if(mRightLogoView !=null){
            mRightLogoView.getBackground().setAlpha(alpha);
        }
    }
    //确保左边LogoView被实例化
    private void ensureLeftLogoView() {
        mLeftLayout.removeAllViews();
        mLeftLayout.setOnClickListener(null);
        if (mLeftLogoView == null) {
            mLeftLogoView = new ImageView(getContext());
            mLeftLogoView.setLayoutParams(new LayoutParams(
                    mLogoSize,
                    mLogoSize
            ));
            mLeftLogoView.setBackground(getLogoBackground());
            mLeftLogoView.setVisibility(GONE);
        }
        if(mLogoDescription == null){
            mLogoDescription = new TextView(getContext());
            mLogoDescription.setVisibility(GONE);
            mLogoDescription.setTextSize(mSubtitleTextSize);
            mLogoDescription.setTextColor(mSubtitleTextColor);
        }else {
            mLogoDescription.setText(null);
        }
        mLeftLogoView.setOnClickListener(null);
        mLeftLayout.addView(mLeftLogoView);
        mLeftLayout.addView(mLogoDescription);
    }
    //标题
    public void setTitle(CharSequence text) {
        if(!TextUtils.isEmpty(text)){
            ensureTitleView();
            mTitle.setText(text);
            mTitle.setVisibility(VISIBLE);
        }
    }
    //标题颜色
    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    //标题字体大小
    public void setTitleSize(float size) {
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    }
    //副标题
    public void setSubtitle(CharSequence text) {
        if(!TextUtils.isEmpty(text)){
            ensureTitleView();
            mSubtitle.setText(text);
            mSubtitle.setVisibility(VISIBLE);
        }
    }
    //副标题颜色
    public void setSubtitleColor(int color) {
        mSubtitle.setTextColor(color);
    }

    //副标题字体大小
    public void setSubtitleSize(float size) {
        mSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    }
    //确保标题被实例化
    private void ensureTitleView(){
        mCoreLayout.removeAllViews();
        mCoreLayout.setOrientation(LinearLayout.VERTICAL);
        if(mTitle == null){
            mTitle = new TextView(getContext());
            mTitle.setVisibility(GONE);
            mTitle.setTextSize(mTitleTextSize);
            mTitle.setTextColor(mTitleTextColor);
        }
        if(mSubtitle == null){
            mSubtitle = new TextView(getContext());
            mSubtitle.setVisibility(GONE);
            mSubtitle.setTextSize(mSubtitleTextSize);
            mSubtitle.setTextColor(mSubtitleTextColor);
        }
        mCoreLayout.addView(mTitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mCoreLayout.addView(mSubtitle, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    private Drawable getLogoBackground(){
        Drawable normalDraw = new DrawableHelper.RoundBackgroundBuilder(getContext())
                .create();
        Drawable pressedDraw = new DrawableHelper.RoundBackgroundBuilder(getContext())
                .setSize(mLogoSize,mLogoSize)
                .setSolidColor(mLogoPressedBackgroundColor)
                .create();
        pressedDraw.setAlpha(50);
        return DrawableHelper.getSelector(normalDraw,pressedDraw);
    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            mTintManager = new SystemBarTintManager(mActivity);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
        }
    }
    //状态栏和功能键颜色
    public void setTintColor(int color){
        mTintManager.setTintColor(color);
    }
    //状态栏颜色
    public void setStatusBarTintColor(int color){
        mTintManager.setStatusBarTintColor(color);
    }
    //功能键颜色
    public void setFunctionBarTintColor(int color){
        mTintManager.setNavigationBarTintColor(color);
    }
    //设置中心布局
    public void setCoreLayoutViews(CoreLayout views){
        if(views != null){
            mCoreLayout.removeAllViews();
            mCoreLayout.addView(views.setCoreLayoutViews());
        }
    }
    //设置左边布局
    public void setLeftLayoutViews(LeftLayout views){
        if(views != null){
            mLeftLayout.removeAllViews();
            mLeftLayout.addView(views.setLeftLayoutViews());
        }
    }
    //设置右边布局
    public void setRightLayoutViews(RightLayout views){
        if(views != null){
            mRightLayout.removeAllViews();
            mRightLayout.addView(views.setRightLayoutViews());
        }
    }
    public interface LeftLayout{

        public View setLeftLayoutViews();
    }
    public interface RightLayout{

        public View setRightLayoutViews();
    }
    public interface CoreLayout{

        public View setCoreLayoutViews();
    }

}
