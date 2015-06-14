package com.example.myapp;

import android.os.Handler;
import com.example.myapp.datamodel.PhotosData;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Artem on 14.06.2015.
 */
public class InstagramPhotoLoader {

    private final OnPhotoLoadListener mListener;
    private final Handler mUIHandler;
    private PhotoInfo[] mPhotoInfos;

    public interface OnPhotoLoadListener {
        void onPhotosLoaded(PhotoInfo[] mPhotoInfos);
    }

    public InstagramPhotoLoader (Handler uiHandler, OnPhotoLoadListener listener){
        mListener = listener;
        mUIHandler = uiHandler;
    }
    public void loadUserPhotos(final String nick) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet getMediaForUser = new HttpGet("https://api.instagram.com/v1/users/" + nick + "/media/recent/?client_id=a2b04732b52d43c99fe453a8ca2a5512&count=20");
                String mediaResponseBody = null;
                int resCode = 0;
                try {
                    HttpResponse mediaResponse = client.execute(getMediaForUser);
                    int responseCode = mediaResponse.getStatusLine().getStatusCode();
                    resCode = responseCode;

                    switch(responseCode) {
                        case 200:
                            HttpEntity entity = mediaResponse.getEntity();
                            if(entity != null) {
                                mediaResponseBody = EntityUtils.toString(entity);
                            }
                            break;
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PhotosData photos = null;
                if(mediaResponseBody != null) {
                    photos = new Gson().fromJson(mediaResponseBody, PhotosData.class);
                    mPhotoInfos = photos.data;
                }
                mUIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onPhotosLoaded(mPhotoInfos);
                        }
                    }
                });
            }
        }).start();
    }
}
