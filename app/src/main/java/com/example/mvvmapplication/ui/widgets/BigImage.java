package com.example.mvvmapplication.ui.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class BigImage extends View implements GestureDetector.OnGestureListener,View.OnTouchListener, GestureDetector.OnDoubleTapListener {

    private final Rect mRect;
    private final BitmapFactory.Options mOption;
    private final GestureDetector mGestureDetector;
    private final Scroller mScroller;
    private final ScaleGestureDetector mScaleGestureDectector;
    private int mImageWidth;
    private int mImageHeight;
    private BitmapRegionDecoder mDecoder;
    private int mViewWidth;
    private int mViewHeight;
    private float mScale;
    private Bitmap mBitMap;
    private float originalScale;

    public BigImage(Context context) {
        this(context,null);
    }

    public BigImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BigImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRect = new Rect();
        mOption = new BitmapFactory.Options();
        mGestureDetector = new GestureDetector(context,this);
        mScroller = new Scroller(context);

        // 缩放手势
        mScaleGestureDectector = new ScaleGestureDetector(context,new ScaleGesture());

        setOnTouchListener(this);
    }

    public void setImage(InputStream inputStream){
        // 获取图片信息，不将整张图片加载到内存
        mOption.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(inputStream,null,mOption);
        mImageWidth = mOption.outWidth;
        mImageHeight = mOption.outHeight;

        // 开启复用
        mOption.inMutable = true;
        // 设置格式
        mOption.inPreferredConfig = Bitmap.Config.RGB_565;

        mOption.inJustDecodeBounds = false;

        try {
            mDecoder = BitmapRegionDecoder.newInstance(inputStream,false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        // 确定加载图片的区域
//        mRect.left = 0;
//        mRect.top = 0;
//        mRect.right = mImageWidth;
//        mScale = mViewWidth / (float)mImageWidth;
//        mRect.bottom = (int)(mViewHeight / mScale);

        // 增加手势缩放后的逻辑
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = Math.min(mImageWidth,mViewWidth);
        mRect.bottom = Math.min(mImageHeight,mViewHeight);

        originalScale = mViewWidth /(float)mImageWidth;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mDecoder == null){
            return;
        }
        // 复用内存(复用bitmap的大小必须和即将解码的bitmap一样）
        mOption.inBitmap = mBitMap;
        mBitMap = mDecoder.decodeRegion(mRect,mOption);
        Matrix matrix = new Matrix();
        matrix.setScale(mViewWidth / (float)mRect.width(),mViewWidth / (float)mRect.width());
        canvas.drawBitmap(mBitMap,matrix,null);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // 如果移动没有停止，就强行停止
        if(!mScroller.isFinished()){
            mScroller.forceFinished(true);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mRect.offset((int)distanceX,(int)distanceY);
        if(mRect.bottom > mImageHeight){
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - (int)(mViewHeight/mScale);
        }
        if(mRect.top < 0){
            mRect.top = 0;
            mRect.bottom = (int)(mViewHeight/mScale);
        }
        if(mRect.right > mImageWidth){
            mRect.right = mImageWidth;
            mRect.left = mImageWidth - (int)(mImageWidth/mScale);
        }
        if(mRect.left < 0){
            mRect.left = 0;
            mRect.right = (int)(mViewWidth/mScale);
        }

        invalidate();


        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    // 处理惯性问题
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mRect.left,mRect.top,(int)-velocityX,
                (int)-velocityY,0,mImageWidth - (int)(mViewHeight/mScale),0,
                mImageHeight-(int)(mViewHeight/mScale));
        return false;
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if(mScroller.isFinished()){
            return;
        }
        if(mScroller.computeScrollOffset()){
            mRect.top = mScroller.getCurrY();
            mRect.bottom = mRect.top + (int)(mViewHeight / mScale);
            invalidate();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
         // 直接将事件传递给手势事件处理
         mGestureDetector.onTouchEvent(event);

         mScaleGestureDectector.onTouchEvent(event);

         return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if(mScale < originalScale * 1.5){
            mScale = originalScale * 3;
        }else{
            mScale = originalScale;
        }
        mRect.right = mRect.left + (int)(mViewHeight / mScale);
        mRect.bottom = mRect.top + (int)(mViewHeight / mScale);

        if(mRect.bottom > mImageHeight){
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - (int)(mViewHeight/mScale);
        }
        if(mRect.top < 0){
            mRect.top = 0;
            mRect.bottom = (int)(mViewHeight/mScale);
        }
        if(mRect.right > mImageWidth){
            mRect.right = mImageWidth;
            mRect.left = mImageWidth - (int)(mImageWidth/mScale);
        }
        if(mRect.left < 0){
            mRect.left = 0;
            mRect.right = (int)(mViewWidth/mScale);
        }

        invalidate();


        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    // 处理缩放
    class ScaleGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = mScale;
            scale += detector.getScaleFactor() - 1;
            if(scale <= originalScale){
                scale = originalScale;
            }else if(scale > originalScale){
                scale = originalScale * 5;
            }
            mRect.right = mRect.left + (int)(mViewWidth / scale);
            mRect.bottom = mRect.top + (int)(mViewHeight / scale);
            mScale = scale;
            invalidate();
            return true;
        }
    }
}
