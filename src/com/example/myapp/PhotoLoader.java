package com.example.myapp;

import android.content.res.Resources;

/**
 * Created by Artem on 06.06.2015.
 */
public abstract class PhotoLoader {
    public abstract void loadPhotos(PhotoInfo[] photoInfos, Resources res);
}
