package com.example.yupenglei.yu_rxandroid.fragment.ui;

import android.util.Log;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by yupenglei on 17/4/5.
 */

public class ThreeFragment extends MidLayerFragment {

    private Subscription mSubscription;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected Observable<AppInfo> getObservable() {
        mSubscription = Observable.interval(3, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        doCompelet("three");
                    }

                    @Override
                    public void onError(Throwable e) {
                        doError();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(">>>", "I say " + aLong);
                    }
                });
        List<AppInfo> list = ApplicationList.getInstance().getList();
        return Observable.just(list.get(0), list.get(1), list.get(2)).repeat(10);
    }

}
