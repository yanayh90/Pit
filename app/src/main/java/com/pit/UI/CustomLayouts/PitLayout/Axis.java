package com.pit.UI.CustomLayouts.PitLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

import com.pit.R;

/**
 * Axis object. draw X, Y axis
 */
public class Axis extends View {

    private Paint mPaint;

    public Axis(Context context) {
        super(context);

        mPaint = new Paint();

        mPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorAxis, null));
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mPaint);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
    }
}
