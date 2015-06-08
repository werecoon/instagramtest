package com.example.myapp;

import android.graphics.drawable.Drawable;

/**
 * Created by Artem on 06.06.2015.
 */
public class PhotoInfo {

    public Drawable mDrawable;
    private String mName;
    private int likesCount;
    private boolean isSelected;

    public PhotoInfo(String name) {
        mName = name;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public String getName() {
        return mName;
    }

    public boolean isSelcted() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }
}
