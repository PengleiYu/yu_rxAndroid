package com.example.yupenglei.yu_rxandroid.fragment.ui.item8.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UsersResponse {

    @SerializedName("items")
    @Expose
    public List<User> users;
}
