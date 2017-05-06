package com.pit.UI.CustomLayouts.PitLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;


import com.pit.R;
import com.pit.Utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by Yanay on 05/05/2017.
 */

public class Graph extends View {

    ///////////////////////////////
    // Constants
    ///////////////////////////////

    public final int RADIUS = 20;

    ///////////////////////////////
    // Fields
    ///////////////////////////////

    /**
     * Paint to draw circles
     */
    private Paint mPointPaint = new Paint();
    private Paint mLinePaint = new Paint();

    /**
     * All available circles
     */
    public List<Point> mPoints = new ArrayList<>();
    private SparseArray<Point> mPointPointer = new SparseArray<>();

    private int mCanvasWidth;
    private int mCanvasHeight;

    /**
     * Default constructor
     *
     * @param ct {@link android.content.Context}
     */
    public Graph(final Context ct) {
        super(ct);

        init(ct);
    }

    public Graph(final Context ct, final AttributeSet attrs) {
        super(ct, attrs);

        init(ct);
    }

    public Graph(final Context ct, final AttributeSet attrs, final int defStyle) {
        super(ct, attrs, defStyle);

        init(ct);
    }

    private void init(final Context ct) {

        mPointPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPoint, null));
        mLinePaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorLine, null));
    }

    @Override
    public void onDraw(final Canvas canvas) {

        // get the center position of the screen
        mCanvasWidth = getWidth();
        mCanvasHeight = getHeight();

        // sort the Points Array to keep the graph simple
        sort();

        // Draw the Points and the Lines
        for (int i = 0; i < mPoints.size(); i++) {

            Point currentCircle = mPoints.get(i);

            if (i > 0) {

                Point prevCircle = mPoints.get(i - 1);

                canvas.drawLine(prevCircle.getCenterX(), prevCircle.getCenterY(), currentCircle.getCenterX(), currentCircle.getCenterY(), mLinePaint);
            }

            canvas.drawCircle(currentCircle.getCenterX(), currentCircle.getCenterY(), RADIUS, mPointPaint);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {

        boolean handled = false;

        Point touchedPoint;
        int xTouch;
        int yTouch;
        int pointerId;
        int actionIndex = event.getActionIndex();

        // get touch event coordinates and make transparent circle from it
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                // it's the first pointer, so clear all existing pointers data
                clearCirclePointer();

                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);

                // check if we've touched inside some circle
                touchedPoint = getTouchedPoint(xTouch, yTouch);

                if (touchedPoint != null) {

                    touchedPoint.setCenterX(xTouch);
                    touchedPoint.setCenterY(yTouch);
                    mPointPointer.put(event.getPointerId(0), touchedPoint);
                }

                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                xTouch = (int) event.getX(actionIndex);
                yTouch = (int) event.getY(actionIndex);

                // check if we've touched inside some circle
                touchedPoint = getTouchedPoint(xTouch, yTouch);

                if (touchedPoint != null) {

                    touchedPoint.setCenterX(xTouch);
                    touchedPoint.setCenterY(yTouch);
                    mPointPointer.put(event.getPointerId(0), touchedPoint);
                }

                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_MOVE:

                final int pointerCount = event.getPointerCount();

                for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {

                    // Some pointer has moved, search it by pointer id
                    pointerId = event.getPointerId(actionIndex);

                    xTouch = (int) event.getX(actionIndex);
                    yTouch = (int) event.getY(actionIndex);

                    touchedPoint = mPointPointer.get(pointerId);

                    if (null != touchedPoint) {

                        touchedPoint.setCenterX(xTouch);
                        touchedPoint.setCenterY(yTouch);
                    }
                }

                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_UP:

                clearCirclePointer();
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:

                // not general pointer was up
                pointerId = event.getPointerId(actionIndex);

                mPointPointer.remove(pointerId);
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:
                handled = true;
                break;

            default:
                break;
        }

        return super.onTouchEvent(event) || handled;
    }


    /**
     * Sort the Points to keep the graph simple
     */
    public void sort() {
        Collections.sort(mPoints, new Comparator<Point>() {

            public int compare(Point p1, Point p2) {

                if (p1.getCenterX() == p2.getCenterX()) {

                    return 0;
                }

                return p1.getCenterX() < p2.getCenterX() ? -1 : 1;
            }
        });
    }

    /**
     * create new Point and add it to the Points Array
     *
     * @param inRandomLocation boolean true to create Point in a random location,
     *                         false for new point in (0,0)
     */
    public void addCirclePoint(Boolean inRandomLocation) {

        Point newPoint;

        // calculate the view size
        int mWidth = mCanvasWidth - RADIUS;
        int mHeight = mCanvasHeight - RADIUS;

        if (inRandomLocation) {

            int randomWidth, randomHeight;

            int maxWidth = this.getResources().getDisplayMetrics().widthPixels;
            int minWidth = RADIUS;
            int maxHeight = this.getResources().getDisplayMetrics().widthPixels;
            int minHeight = RADIUS;

            Random randomLocation = new Random();
            randomWidth = randomLocation.nextInt(maxWidth - minWidth + 1) + minWidth;
            randomHeight = randomLocation.nextInt(maxHeight - minHeight + 1) + minHeight;

            newPoint = new Point(randomWidth, randomHeight, RADIUS);

        } else {

            newPoint = new Point(mWidth / 2, mHeight / 2, RADIUS);
        }

        mPoints.add(newPoint);

        Logger.v("Point Added Successfully");

        invalidate();
    }

    /**
     * Clears all CircleArea - pointer id relations
     */
    private void clearCirclePointer() {

        mPointPointer.clear();
    }

    /**
     * Determines touched point
     *
     * @param xTouch int x touch coordinate
     * @param yTouch int y touch coordinate
     * @return {@link Point} touched circle or null if no circle has been touched
     */
    private Point getTouchedPoint(final int xTouch, final int yTouch) {

        Point touched = null;

        for (Point circle : mPoints) {

            if ((circle.getCenterX() - xTouch) * (circle.getCenterX() - xTouch) + (circle.getCenterY() - yTouch) * (circle.getCenterY() - yTouch) <= circle.getRadius() * circle.getRadius()) {

                touched = circle;
                break;
            }
        }

        return touched;
    }
}
