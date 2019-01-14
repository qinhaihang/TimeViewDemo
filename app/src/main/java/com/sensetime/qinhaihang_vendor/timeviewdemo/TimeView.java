package com.sensetime.qinhaihang_vendor.timeviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/1/14 14:17
 * @des
 * @packgename com.sensetime.qinhaihang_vendor.timeviewdemo
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class TimeView extends View {

    private static final String TAG = "TimeView";

    private Paint mDefaultPaint;
    private boolean mShowWeek;
    private boolean mShowDate;
    private boolean mShowTime;
    private String mTimeStr;

    int defaultWidth = 300;
    int defaultHeight = 200;

    public TimeView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TimeView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TimeView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
        mShowWeek = typedArray.getBoolean(R.styleable.TimeView_showWeek, true);
        mShowDate = typedArray.getBoolean(R.styleable.TimeView_showDate, true);
        mShowTime = typedArray.getBoolean(R.styleable.TimeView_showTime, true);
        typedArray.recycle();

        mDefaultPaint = getPaint(1, Paint.Style.FILL, Color.BLACK);
    }

    private void initCalendar() {

        long time = System.currentTimeMillis();
        Date date = new Date(time);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        String MonthADay = dateFormat.format(date);

        SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
        String week = weekFormat.format(date);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        mTimeStr = timeFormat.format(date);
    }

    /**
     * 设置画笔
     *
     * @param strokeWidth 画笔宽度
     * @param style       画笔风格
     * @param color       画笔颜色
     * @return
     */
    private Paint getPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.i(TAG, "widthMode = " + widthMode + ", withSize = " + withSize);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "heightMode = " + heightMode + ", heightSize = " + heightSize);

        if(getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT
                && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,defaultHeight);
        } else if(getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,heightSize);
        } else if(getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(withSize,defaultHeight);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");

        initCalendar();
        mDefaultPaint.setTextSize(30);
        /*
            注意点： 参数 x，y是绘制的文字的第一个字的左下角的 x、y值
            如果输入参数 x=0,y=0 则绘制的文字无法显示在View上
         */
        canvas.drawText(mTimeStr, 0, 30, mDefaultPaint);
        //测量文字的宽度
        float timeWidth = mDefaultPaint.measureText(mTimeStr);

        defaultWidth = (int)timeWidth;
        defaultHeight = 40;
        Log.i(TAG, "timeWidth = " + timeWidth + ", timeHeight = ");
        //invalidate(); //只会调用 draw
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");
    }
}
