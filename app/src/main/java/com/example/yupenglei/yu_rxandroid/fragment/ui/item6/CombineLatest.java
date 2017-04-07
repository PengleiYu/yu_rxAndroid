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
 * Created by yupenglei on 17/4/7.
 */

public class CombineLatest extends MidLayerFragment {
    @Override
    protected Observable<AppInfo> getObservable() {
        Observable<AppInfo> appSequence = Observable.interval(1, TimeUnit.SECONDS)
                .map(new Func1<Long, AppInfo>() {
                    @Override
                    public AppInfo call(Long aLong) {
                        return ApplicationList.getInstance().getList().get(aLong.intValue());
                    }
                });
        Observable<Long> interval = Observable.interval(1500, TimeUnit.MILLISECONDS);
        return Observable.combineLatest(appSequence, interval,
                new Func2<AppInfo, Long, AppInfo>() {
                    @Override
                    public AppInfo call(AppInfo info, Long aLong) {
                        info.setName(aLong + " " + info.getName());
                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
