package com.example.administrator.fightthelandlord.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

/**
 * 电脑画板
 **/
public class TableView extends View {
    public ArrayList<String> arrayList = new ArrayList<>();
    private int mColumnSize, mRowSize;
    private int NUM_COLUMNS = 1;
    private int NUM_ROWS = 1;
    private Paint mPaint;
    private int mCardSize = 18;
    private DisplayMetrics mDisplayMetrics;

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        mPaint = new Paint();
    }

    public void setColumnAndRow(int column,int row){
        this.NUM_COLUMNS = column;
        this.NUM_ROWS = row;
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
        mPaint.setAntiAlias(true);
        if (arrayList == null) return;
        int RowCount = arrayList.size() / NUM_COLUMNS;
        for (int Card = 0; Card < arrayList.size(); Card++) {
            int column = Card % NUM_COLUMNS;
            int row = Card / NUM_COLUMNS;

            //绘制背景色矩形
            int startRecX, startRecY;
            if (row < RowCount) {
                startRecX = mColumnSize * column;
            } else {
                startRecX = (getWidth() - arrayList.size() % NUM_COLUMNS * mColumnSize) / 2 + column * mColumnSize;
            }
            startRecY = (getHeight() - RowCount * mRowSize) / 2 + row * mRowSize;
            int endRecX = startRecX + mColumnSize;
            int endRecY = startRecY + mRowSize;

            mPaint.setColor(Color.GRAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(startRecX, startRecY, endRecX, endRecY, 20, 20, mPaint);
            } else {
                canvas.drawRect(startRecX, startRecY, endRecX, endRecY, mPaint);
            }

            String string = arrayList.get(Card);
            float startX = startRecX + (mColumnSize - mPaint.measureText(string)) / 2;
            float startY = startRecY + mRowSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2;
            mPaint.setColor(Color.BLACK);
            canvas.drawText(string, startX, startY, mPaint);
        }
    }
}
