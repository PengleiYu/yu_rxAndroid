package com.example.yupenglei.yu_rxandroid.fragment.ui.item5;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by yupenglei on 17/4/6.
 */

public class MapFragment extends MidLayerFragment {

    @Override
    protected void loadApps() {
        Observable.from(ApplicationList.getInstance().getList())
                .map(new Func1<AppInfo, AppInfo>() {
                    @Override
                    public AppInfo call(AppInfo info) {
                        info.setName(info.getName().toLowerCase());
                        return info;
                    }
                })
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        doCompelet("map");
                    }

                    @Override
                    public void onError(Throwable e) {
                        doError();
                    }

                    @Override
                    public void onNext(AppInfo info) {
                        mAdapter.addAppInfo(info);
                    }
                });
    }
}
