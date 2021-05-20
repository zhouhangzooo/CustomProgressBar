package com.t.progressbarapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.t.progressbarapp.R;

public class TestProgressBar extends View {

    private Paint paint;
    private int color;
    private int bgSpace = 4; //背景间距
    private int bgSpaceX = 2; //背景间距

    /**
     * 最大进度
     */
    private int max;

    /**
     * 当前进度
     */
    private int progress;

    public TestProgressBar(Context context) {
        this(context, null);
    }

    public TestProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TestProgressBar);
        color = mTypedArray.getColor(R.styleable.TestProgressBar_progressColor, Color.RED);

        paint = new Paint();
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (max == 0) {
            return;
        }
        canvas.drawRect(bgSpaceX, bgSpace, ((getWidth() - bgSpaceX * 2) * progress / max) + bgSpaceX, getHeight() - bgSpace, paint);
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            return;
        }
        this.max = max;
    }

    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            return;
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }
    }

}
