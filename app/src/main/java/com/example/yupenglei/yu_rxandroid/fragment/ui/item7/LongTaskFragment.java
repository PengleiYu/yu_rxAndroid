package com.example.yupenglei.yu_rxandroid.fragment.ui.item7;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by yupenglei on 17/4/10.
 */

public class LongTaskFragment extends MidLayerFragment {
    @Override
    protected Observable<AppInfo> getObservable() {
        return Observable.create(new Observable.OnSubscribe<AppInfo>() {
            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {
                for (double i = 0; i < 100000000; i++) {
                    double y = i * i;
                }
                for (AppInfo info : ApplicationList.getInstance().getList()) {
                    subscriber.onNext(info);
                }
                subscriber.onCompleted();
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.computation());
    }
}
