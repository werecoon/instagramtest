package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
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
        int w = 400, h = 400;

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(bmp);
        Bitmap enot = BitmapFactory.decodeResource(getResources(), R.drawable.enot);
        Bitmap rama = BitmapFactory.decodeResource(getResources(), R.drawable.rama);
        Rect ramaRect = new Rect(50, 50, 350, 350);
        Rect enotRect = new Rect(100, 100, 300, 300);
        canvas.drawBitmap(rama, null, ramaRect, null);
        canvas.drawBitmap(enot, null, enotRect, null);
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
