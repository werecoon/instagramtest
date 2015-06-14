package com.example.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Artem on 14.06.2015.
 */
public class UserPhotoListActivity extends Activity implements InstagramPhotoLoader.OnPhotoLoadListener, AdapterView.OnItemClickListener {

    private Button mMakeCollageeButton;
    private ListView mImagesListView;
    private InstagramPhotoLoader mImagesLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mMakeCollageeButton = (Button) findViewById(R.id.make_collage);
        mImagesListView = (ListView) findViewById(R.id.photos_list);
        String nick = getIntent().getStringExtra("userid");
        mImagesLoader = new InstagramPhotoLoader(new Handler(), this);
        mImagesLoader.loadUserPhotos(nick);
    }

    @Override
    public void onPhotosLoaded(PhotoInfo[] photoInfos) {
        mImagesListView.setAdapter(new PhotosAdapter(this, R.id.photo_view, photoInfos));
        mImagesListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoInfo info = (PhotoInfo) mImagesListView.getAdapter().getItem(position);
        if (info.isSelcted()) {
            info.setIsSelected(false);
        } else {
            info.setIsSelected(true);
        }
        View marker = view.findViewById(R.id.photo_marker);
        if (info.isSelcted()) {
            marker.setVisibility(View.VISIBLE);
        } else {
            marker.setVisibility(View.INVISIBLE);
        }
    }
}
