package com.example.yupenglei.yu_rxandroid.fragment.ui.item6;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

/**
 * Created by yupenglei on 17/4/6.
 */

public class ZipFragment extends MidLayerFragment {
    @Override
    protected Observable<AppInfo> getObservable() {
        Observable<Long> observable1 = Observable.interval(1, TimeUnit.SECONDS);
        Observable<AppInfo> observable2 = Observable.from(ApplicationList.getInstance().getList());
        return Observable.zip(observable1, observable2, new Func2<Long, AppInfo, AppInfo>() {
            @Override
            public AppInfo call(Long aLong, AppInfo info) {
                info.setName(aLong + " " + info.getName());
                return info;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }
}
