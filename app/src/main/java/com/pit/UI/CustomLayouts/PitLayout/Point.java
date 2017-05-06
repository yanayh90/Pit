package com.pit.UI.CustomLayouts.PitLayout;

/**
 * Point object.
 */
public class Point {

    private int mRadius;
    private int mCenterX;
    private int mCenterY;

    public Point(int centerX, int centerY, int radius) {

        mRadius = radius;
        mCenterX = centerX;
        mCenterY = centerY;
    }

    public void setCenterX(int centerX) {

        mCenterX = centerX;
    }

    public int getCenterX() {

        return mCenterX;
    }

    public void setCenterY(int centerY) {

        mCenterY = centerY;
    }

    public int getCenterY() {

        return mCenterY;
    }

    public int getRadius() {

        return mRadius;
    }
}