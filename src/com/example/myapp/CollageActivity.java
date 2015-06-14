package com.example.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Artem on 06.06.2015.
 */
public class CollageActivity extends Activity {
    public static final String COLAGE_BITMAP = "collage_bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collage_layout);
//        ((ImageView)findViewById(R.id.collage_result)).setImageBitmap(ChooseUserActivity.COlLAGE);
    }
}
