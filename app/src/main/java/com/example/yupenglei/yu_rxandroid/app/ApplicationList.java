package com.example.yupenglei.yu_rxandroid.app;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by yupenglei on 17/3/31.
 */

@Accessors(prefix = "m")
public class ApplicationList {
    @Getter
    @Setter
    private List<AppInfo> mList;

    private static ApplicationList sApplicationList = new ApplicationList();

    public static ApplicationList getInstance() {
        return sApplicationList;
    }
}
