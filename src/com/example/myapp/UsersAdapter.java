package com.example.myapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapp.datamodel.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Artem on 14.06.2015.
 */
public class UsersAdapter extends ArrayAdapter<User> {

    public UsersAdapter(Context context, int resource, User[] objects) {
        super(context, resource, objects);
    }

    static class ViewHolder {
        private ImageView image;
        private TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder = null;

        if(convertView == null){

            mViewHolder = new ViewHolder();

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.photo_element_layout, parent, false);
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.photo_view);
            mViewHolder.name = (TextView) convertView.findViewById(R.id.photo_name);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder =   (ViewHolder) convertView.getTag();
        }
        User userItem = getItem(position);
        final ViewHolder finalMViewHolder = mViewHolder;
        Picasso.with(getContext()).load(userItem.profile_picture).into(mViewHolder.image);
        mViewHolder.name.setText(userItem.username);

        return convertView;
    }
}
