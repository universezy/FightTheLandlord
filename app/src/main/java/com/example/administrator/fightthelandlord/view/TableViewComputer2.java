package com.example.administrator.fightthelandlord.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/22.
 */

public class TableViewComputer2 extends View {
    public ArrayList<String> arrayList;
    private int mColumnSize, mRowSize;
    private static final int NUM_COLUMNS = 5;
    private static final int NUM_ROWS = 4;
    private Paint mPaint;
    private int mCardSize = 18;
    private DisplayMetrics mDisplayMetrics;

    public TableViewComputer2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        mPaint = new Paint();
    }

    public void setContent(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mColumnSize = getWidth() / NUM_COLUMNS;
        mRowSize = getHeight() / NUM_ROWS;
        mPaint.setTextSize(mCardSize * mDisplayMetrics.scaledDensity * 2 / 3);
        //  mPaint.setTextSize(mCardSize);
        mPaint.setAntiAlias(true);
        if (arrayList == null) return;
        for (int Card = 0; Card < arrayList.size(); Card++) {
            int column = Card % NUM_COLUMNS;
            int row = Card / NUM_COLUMNS;

            //绘制背景色矩形
            int startRecX = mColumnSize * column;
            int startRecY = mRowSize * row;
            int endRecX = startRecX + mColumnSize;
            int endRecY = startRecY + mRowSize;
            mPaint.setColor(Color.GRAY);
            canvas.drawRoundRect(startRecX, startRecY, endRecX, endRecY, 30, 30, mPaint);

            String string = arrayList.get(Card);
            int startX = (int) (mColumnSize * column + (mColumnSize - mPaint.measureText(string)) / 2);
            int startY = (int) (mRowSize * row + mRowSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            mPaint.setColor(Color.BLACK);
            canvas.drawText(string, startX, startY, mPaint);
        }
    }
}
