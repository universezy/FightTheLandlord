package com.example.administrator.fightthelandlord.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Administrator on 2017/3/22.
 */

public class TableViewResult extends View {
    public String result;
    private int mColumnSize, mRowSize;
    private static final int NUM_COLUMNS = 5;
    private static final int NUM_ROWS = 4;
    private Paint mPaint;
    private int mCardSize = 25;
    private DisplayMetrics mDisplayMetrics;

    public TableViewResult(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        mPaint = new Paint();
    }

    public void setContent(String result) {
        this.result = result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
