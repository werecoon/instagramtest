package com.example.myapp.datamodel;

import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by Artem on 14.06.2015.
 */
public class UsersData {

    @SerializedName("data")
    private User[] data;

    public User[] getUsers() {
        return data;
    }
}
