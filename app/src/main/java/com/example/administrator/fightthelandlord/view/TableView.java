package com.example.administrator.fightthelandlord.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义画板
 **/
public class TableView extends View {
    public String stringContent = "";

    public TableView(Context context) {
        super(context);
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setContent(String string){
        this.stringContent = string;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor( Color.BLUE );
        paint.setAntiAlias( true );
        paint.setTextSize( 80 );

        float startX = (getWidth() - paint.measureText(stringContent)) / 2;
        float startY = getHeight() / 2 - (paint.ascent() + paint.descent()) / 2;

        canvas.drawText(stringContent,startX, startY, paint );

        super.onDraw(canvas);
    }
}
