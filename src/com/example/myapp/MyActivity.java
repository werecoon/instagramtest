package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button mLoadImagesButton;
    private EditText mUserNameField;
    private Button mMakeCollageeButton;
    private ListView mPhotosList;
    private PhotoInfo[] mPhotoInfos;
    private ImageView mCollageView;
    public static Bitmap COlLAGE;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mLoadImagesButton = (Button) findViewById(R.id.load_images_button);
        mUserNameField = (EditText) findViewById(R.id.userNameField);
        mMakeCollageeButton = (Button) findViewById(R.id.make_collage);
        mPhotosList = (ListView) findViewById(R.id.photos_list);
        mCollageView = (ImageView) findViewById(R.id.collage_result);
        mLoadImagesButton.setOnClickListener(this);
        mUserNameField.setOnClickListener(this);
        mMakeCollageeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.load_images_button) {
            String name = mUserNameField.getText().toString();
            mPhotoInfos = new PhotoInfo[10];
            for (int i = 0; i < mPhotoInfos.length; i++){
                mPhotoInfos[i] = new PhotoInfo(name + " " + "photo" + " " + i);
            }
            PhotoLoader loader = new LocalPhotoLoader();
            loader.loadPhotos(mPhotoInfos, getResources());
            mPhotosList.setAdapter(new PhotosAdapter(this, R.id.photo_view, mPhotoInfos));
            mPhotosList.setOnItemClickListener(this);
        } else if (v.getId() == R.id.userNameField) {
//            Toast.makeText(this, "Load images button clicked", Toast.LENGTH_LONG);
        } else if (v.getId() == R.id.make_collage) {
            makeCollage();
        } else if (v.getId() == R.id.photos_list) {
//            Toast.makeText(this, "Load images button clicked", Toast.LENGTH_LONG);
        }
    }

    private void makeCollage() {
        List<PhotoInfo> forCollage = new ArrayList<PhotoInfo>();
        for (PhotoInfo info : mPhotoInfos) {
            if (info.isSelcted()) {
                forCollage.add(info);
            }
        }
        Intent intent = new Intent(this, CollageActivity.class);
        COlLAGE = getCollage(forCollage);
        startActivity(intent);
    }

    private Bitmap getCollage(List<PhotoInfo> forCollage) {
        int w = 400, h = 600;

        int paddingHorizontal = 100;
        int paddingVertical = 150;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(bmp);
        if (forCollage.size() > 0) {
            Bitmap enot = BitmapFactory.decodeResource(getResources(), forCollage.get(0).getDrawable());
//            Rect ramaRect = new Rect(50, 50, 350, 350);
            Rect enotRect = new Rect(paddingHorizontal, paddingVertical, w-paddingHorizontal, h-paddingVertical);
            canvas.drawBitmap(enot, null, enotRect, null);
        }
        int lastDrawnPhotoPosition = 0;
        for (int i = 1; i < forCollage.size(); i++) {
            if (i <= w/paddingHorizontal) {
                Bitmap enot = BitmapFactory.decodeResource(getResources(), forCollage.get(i).getDrawable());
                Rect enotRect = new Rect(paddingHorizontal*(i-1), 0, (i)*paddingHorizontal, paddingVertical);
                canvas.drawBitmap(enot, null, enotRect, null);
            } else if (i > w/paddingHorizontal && i <= 2*w/paddingHorizontal) {
                Bitmap enot = BitmapFactory.decodeResource(getResources(), forCollage.get(i).getDrawable());
                Rect enotRect = new Rect(paddingHorizontal*((i - 1 - w/paddingHorizontal)), h - paddingVertical, (i - w/paddingHorizontal)*paddingHorizontal, h);
                canvas.drawBitmap(enot, null, enotRect, null);
            } else if (i > 2*w/paddingHorizontal) {
                Bitmap enot = BitmapFactory.decodeResource(getResources(), forCollage.get(i).getDrawable());
                Rect enotRect = new Rect(paddingHorizontal*(i-1 - 2*w/paddingHorizontal), (i-2*w/paddingHorizontal)*paddingVertical,
                        (i - 2*w/paddingHorizontal)*paddingHorizontal, (i+1-2*w/paddingHorizontal)*paddingVertical);
                canvas.drawBitmap(enot, null, enotRect, null);
            }

        }
        return bmp;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoInfo info = mPhotoInfos[position];
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
