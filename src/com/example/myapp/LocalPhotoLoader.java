package com.example.myapp;

import android.content.res.Resources;

/**
 * Created by Artem on 06.06.2015.
 */
public class LocalPhotoLoader extends PhotoLoader {

    @Override
    public void loadPhotos(PhotoInfo[] photoInfos, Resources res) {
        for (int i = 0; i < photoInfos.length; i++) {
            photoInfos[i].setDrawable(res.getDrawable(R.drawable.enot));
        }
    }
}
