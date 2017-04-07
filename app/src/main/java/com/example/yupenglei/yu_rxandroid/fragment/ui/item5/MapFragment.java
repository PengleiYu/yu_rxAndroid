package com.example.yupenglei.yu_rxandroid.fragment.ui.item5;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yupenglei on 17/4/6.
 */

public class MapFragment extends MidLayerFragment {

    @Override
    protected Observable<AppInfo> getObservable() {
        return Observable.from(ApplicationList.getInstance().getList())
                .map(new Func1<AppInfo, AppInfo>() {
                    @Override
                    public AppInfo call(AppInfo info) {
                        info.setName(info.getName().toLowerCase());
                        return info;
                    }
                });
    }
}
