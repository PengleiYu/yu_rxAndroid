package com.example.yupenglei.yu_rxandroid.app;

import android.support.annotation.NonNull;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by yupenglei on 17/3/31.
 */
@Data
@Accessors(prefix = "m")
public class AppInfo implements Comparable<Object> {
    long mLastUpdateTime;
    String mName;
    String mIcon;

    public AppInfo(String name, String icon, long lastUpdateTime) {
        mName = name;
        mLastUpdateTime = lastUpdateTime;
        mIcon = icon;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        AppInfo info = (AppInfo) o;
        return getName().compareTo(info.getName());
    }
}
