package com.example.mvvmapplication.ui.dashboard.baselayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.mvvmapplication.R;

// 自绘组件
public class CustomDrawWidget extends View {
    private Bitmap mBitmap;

    private Path mPath;
    private PathMeasure mPathMeasure;
    private float mDistanceRatio = 0;
    private Paint mCirclePaint;
    private Paint mBitmapPaint;
    private Matrix mMartrix;


    public CustomDrawWidget(Context context) {
        super(context);
        init();
    }


    private void init(){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_tabbar_lab);

        mPath = new Path();
        mPath.addCircle(0,0,200,Path.Direction.CW);
        mPathMeasure = new PathMeasure(mPath,false);
        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(5);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.GREEN);

        mBitmapPaint = new Paint();
        mBitmapPaint.setColor(Color.DKGRAY);
        mBitmapPaint.setStrokeWidth(2);
        mBitmapPaint.setStyle(Paint.Style.STROKE);

        mMartrix = new Matrix();

        Log.d("TAG", "init called");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.WHITE);
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        // 移动Canvas坐标系到中心
        canvas.translate(w/2,h/2);
        mMartrix.reset();

        mDistanceRatio += 0.006f;
        if(mDistanceRatio >= 1){
            mDistanceRatio = 0;
        }

        float[] pos = new float[2];
        float[] tan = new float[2]; // 记录切点值xy
        float distance = mPathMeasure.getLength() * mDistanceRatio;
        mPathMeasure.getPosTan(distance,pos,tan);
        //tan[0] 代表cos，tan[1] 代表sin
        float degree = (float) (Math.atan2(tan[1],tan[0]) * 180 / Math.PI); // 图片本身要旋转的角度
        mMartrix.postRotate(degree,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
        mMartrix.postTranslate(pos[0] - mBitmap.getWidth()/2,pos[1] - mBitmap.getHeight()/2);
        canvas.drawPath(mPath,mCirclePaint);
        canvas.drawBitmap(mBitmap,mMartrix,mBitmapPaint);

        invalidate();
    }

    public CustomDrawWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDrawWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomDrawWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
}
