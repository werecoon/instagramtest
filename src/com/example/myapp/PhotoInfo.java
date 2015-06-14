package com.example.myapp;

import android.graphics.Bitmap;
import com.example.myapp.datamodel.Caption;
import com.example.myapp.datamodel.Images;
import com.example.myapp.datamodel.Likes;

/**
 * Created by Artem on 06.06.2015.
 */
public class PhotoInfo {

    public Bitmap mDrawable;
    private String mName;
    private int likesCount;
    private boolean isSelected;
    Likes likes;
    Images images;
    Caption caption;

    public PhotoInfo(String name) {
        mName = name;
    }

    public Bitmap getDrawable() {
        return mDrawable;
    }

    public void setBitmap(Bitmap drawable) {
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
