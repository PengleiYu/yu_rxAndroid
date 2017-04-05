package com.example.yupenglei.yu_rxandroid.fragment.ui;

import android.util.Log;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by yupenglei on 17/4/5.
 */

public class ThreeFragment extends MidLayerFragment {

    private Subscription mSubscription;

    @Override
    public void onRefresh() {
        List<AppInfo> list = ApplicationList.getInstance().getList();
        loadApps(list.get(0), list.get(1), list.get(2));
    }

    private void loadApps(AppInfo info1, AppInfo info2, AppInfo info3) {
        Observable.just(info1, info2, info3)
                .repeat(10)
                .subscribe(new Observer<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        doCompelet("three");
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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
