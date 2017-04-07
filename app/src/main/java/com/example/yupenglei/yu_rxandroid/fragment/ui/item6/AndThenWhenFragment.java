package com.example.yupenglei.yu_rxandroid.fragment.ui.item6;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func2;
import rx.joins.Pattern2;
import rx.joins.Plan0;
import rx.observables.JoinObservable;

/**
 * Created by yupenglei on 17/4/7.
 */

public class AndThenWhenFragment extends MidLayerFragment {
    @Override
    protected Observable<AppInfo> getObservable() {
        Observable<AppInfo> apps = Observable.from(ApplicationList.getInstance().getList());
        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);

        Pattern2<AppInfo, Long> pattern = JoinObservable.from(apps).and(interval);
        Plan0<AppInfo> plan = pattern.then(new Func2<AppInfo, Long, AppInfo>() {
            @Override
            public AppInfo call(AppInfo info, Long aLong) {
                info.setName(aLong + " " + info.getName());
                return info;
            }
        });
        return JoinObservable.when(plan).toObservable();
    }

}
