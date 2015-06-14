package com.example.myapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Artem on 06.06.2015.
 */
public class PhotosAdapter extends ArrayAdapter<PhotoInfo> {

    static class ViewHolder {
        private ImageView image;
        private ImageView marker;
        private TextView name;
        private TextView likesCount;
        public boolean visible;
    }

    public PhotosAdapter(Context context, int resource, PhotoInfo[] photoInfos) {
        super(context, resource, photoInfos);
    }

    @Override
     public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder = null;
        if (convertView == null){
            mViewHolder = new ViewHolder();
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.photo_element_layout, parent, false);
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.photo_view);
            mViewHolder.marker = (ImageView) convertView.findViewById(R.id.photo_marker);
            mViewHolder.name = (TextView) convertView.findViewById(R.id.photo_name);
            mViewHolder.likesCount = (TextView) convertView.findViewById(R.id.likes_count_view);
            mViewHolder.likesCount.setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.likes_count_title).setVisibility(View.VISIBLE);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder =   (ViewHolder) convertView.getTag();
        }
        PhotoInfo photoInfo = getItem(position);
        final ViewHolder finalMViewHolder = mViewHolder;
        Bitmap bmp = photoInfo.images.low_resolution.bitmap;

        finalMViewHolder.image.setImageBitmap(bmp);
        if (bmp == null) {
            Picasso.with(getContext()).load(photoInfo.images.low_resolution.url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    finalMViewHolder.image.setImageBitmap(bitmap);
                    photoInfo.images.low_resolution.bitmap = bitmap;
                }

                @Override
                public void onBitmapFailed(Drawable drawable) {}

                @Override
                public void onPrepareLoad(Drawable drawable) {}
            });
        }

        mViewHolder.likesCount.setText("" +  photoInfo.likes.count);
        mViewHolder.name.setText(photoInfo.caption.text);
        if (photoInfo.isSelcted()) {
            mViewHolder.marker.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.marker.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
