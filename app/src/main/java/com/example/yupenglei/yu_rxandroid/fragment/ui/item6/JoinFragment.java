package com.example.yupenglei.yu_rxandroid.fragment.ui.item6;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by yupenglei on 17/4/6.
 */

public class JoinFragment extends MidLayerFragment {
    @Override
    protected Observable<AppInfo> getObservable() {
        Observable<AppInfo> appSequence = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, AppInfo>() {
                    @Override
                    public AppInfo call(Long aLong) {
                        return ApplicationList.getInstance().getList().get(aLong.intValue());
                    }
                });

        Observable<Long> interval = Observable.interval(1000, TimeUnit.MILLISECONDS);

        return appSequence.join(interval,
                new Func1<AppInfo, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(AppInfo info) {
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                }, new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long aLong) {
                        return Observable.timer(0, TimeUnit.SECONDS);
                    }
                }, new Func2<AppInfo, Long, AppInfo>() {
                    @Override
                    public AppInfo call(AppInfo info, Long aLong) {
                        info.setName(aLong + " " + info.getName());
                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }
}
