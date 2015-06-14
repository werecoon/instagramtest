package com.example.myapp;

import android.os.Handler;
import com.example.myapp.datamodel.UsersData;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Artem on 14.06.2015.
 */
public class UsersLoader {
    private final OnUsersDataLoadedListener mListener;
    private final Handler mUIHandler;
    private UsersData mData = null;

    public UsersLoader(OnUsersDataLoadedListener listener, Handler uiHandler) {
        mListener = listener;
        mUIHandler = uiHandler;
    }

    public interface OnUsersDataLoadedListener {
        public void onUsersLoaded(UsersData data);
    }

    public void loadUsers(String byNick) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpGet getRequest = new HttpGet("https://api.instagram.com/v1/users/search?q=" + byNick + "&client_id=a2b04732b52d43c99fe453a8ca2a5512");
                HttpClient client = new DefaultHttpClient();
                int resCode = 0;
                String responseBody = null;
                try {
                    HttpResponse response = client.execute(getRequest);
                    int responseCode = response.getStatusLine().getStatusCode();
                    resCode = responseCode;

                    switch(responseCode) {
                        case 200:
                            HttpEntity entity = response.getEntity();
                            if(entity != null) {
                                responseBody = EntityUtils.toString(entity);
                            }
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(responseBody != null) {
                    mData = new Gson().fromJson(responseBody, UsersData.class);
                }
                if (mListener != null) {
                    mUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onUsersLoaded(mData);
                        }
                    });
                }
            }
        }).start();
    }
}
