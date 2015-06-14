package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.example.myapp.datamodel.User;
import com.example.myapp.datamodel.UsersData;

public class ChooseUserActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener,
        UsersLoader.OnUsersDataLoadedListener {

    private EditText mUserNameField;
    private ListView mUsersList;
    private ImageView mCollageView;
    private Button mLoadUsersButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_list_activity);

        mUserNameField = (EditText) findViewById(R.id.userNameField);
        mLoadUsersButton = (Button) findViewById(R.id.load_users_button);
        mUsersList = (ListView) findViewById(R.id.users_list);
        mUserNameField.setOnClickListener(this);
        mLoadUsersButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.load_users_button) {
            final String nick = mUserNameField.getText().toString();
            if (nick == null || "".equals(nick)) {
                Toast.makeText(this, "Enter user name, please", Toast.LENGTH_LONG).show();
                return;
            }
            UsersLoader usersLoader = new UsersLoader(this, new Handler());
            usersLoader.loadUsers(nick);
        }
    }

//    private void makeCollage() {
//        List<PhotoInfo> forCollage = new ArrayList<PhotoInfo>();
//        for (PhotoInfo info : mPhotoInfos) {
//            if (info.isSelcted()) {
//                forCollage.add(info);
//            }
//        }
//        Intent intent = new Intent(this, CollageActivity.class);
//        COlLAGE = getCollage(forCollage);
//        startActivity(intent);
//    }

//    private Bitmap getCollage(List<PhotoInfo> forCollage) {
//        int w = 400, h = 600;
//
//        int paddingHorizontal = 100;
//        int paddingVertical = 150;
//        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
//        Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
//        Canvas canvas = new Canvas(bmp);
//        if (forCollage.size() > 0) {
//            Bitmap enot = forCollage.get(0).getDrawable();
//            Rect enotRect = new Rect(paddingHorizontal, paddingVertical, w-paddingHorizontal, h-paddingVertical);
//            canvas.drawBitmap(enot, null, enotRect, null);
//        }
//        int lastDrawnPhotoPosition = 0;
//        for (int i = 1; i < forCollage.size(); i++) {
//            if (i <= w/paddingHorizontal) {
//                Bitmap enot = forCollage.get(i).getDrawable();
//                Rect enotRect = new Rect(paddingHorizontal*(i-1), 0, (i)*paddingHorizontal, paddingVertical);
//                canvas.drawBitmap(enot, null, enotRect, null);
//            } else if (i > w/paddingHorizontal && i <= 2*w/paddingHorizontal) {
//                Bitmap enot = forCollage.get(i).getDrawable();
//                Rect enotRect = new Rect(paddingHorizontal*((i - 1 - w/paddingHorizontal)), h - paddingVertical, (i - w/paddingHorizontal)*paddingHorizontal, h);
//                canvas.drawBitmap(enot, null, enotRect, null);
//            } else if (i > 2*w/paddingHorizontal) {
//                Bitmap enot = forCollage.get(i).getDrawable();
//                Rect enotRect = new Rect(paddingHorizontal*(i-1 - 2*w/paddingHorizontal), (i-2*w/paddingHorizontal)*paddingVertical,
//                        (i - 2*w/paddingHorizontal)*paddingHorizontal, (i+1-2*w/paddingHorizontal)*paddingVertical);
//                canvas.drawBitmap(enot, null, enotRect, null);
//            }
//
//        }
//        return bmp;
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String chosenUser = String.valueOf(((User)mUsersList.getAdapter().getItem(position)).id);
        Intent intent = new Intent(this, UserPhotoListActivity.class);
        intent.putExtra("userid", chosenUser);
        startActivity(intent);
    }

    @Override
    public void onUsersLoaded(UsersData data) {
        if( data == null) {
            Toast.makeText(this, "Data loading failed!", Toast.LENGTH_LONG).show();
            return;
        }
        mUsersList.setAdapter(new UsersAdapter(this, R.layout.user_layout, data.getUsers()));
        mUsersList.setOnItemClickListener(this);
    }
}
