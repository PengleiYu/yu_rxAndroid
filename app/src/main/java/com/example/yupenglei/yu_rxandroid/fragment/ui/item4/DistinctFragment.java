package com.example.yupenglei.yu_rxandroid.fragment.ui.item4;

import android.util.Log;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by yupenglei on 17/4/5.
 */

public class DistinctFragment extends MidLayerFragment {

    private Subscription mSubscribe;

    @Override
    public void onRefresh() {
        mAdapter.clear();
        loadApps(ApplicationList.getInstance().getList());
    }

    private void loadApps(List<AppInfo> appInfos) {
        Observable.from(appInfos)
                .take(4)
                .repeat(3)
                .distinct()
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        doCompelet("distinct");
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

        final int[] ints = new int[]{21, 22, 22, 23, 22, 24, 24, 24, 22, 23, 24, 25, 26, 27, 28};
        mSubscribe = Observable.interval(3, TimeUnit.SECONDS)
//                .map(new Func1<Long, Integer>() {
//                    @Override
//                    public Integer call(Long aLong) {
//                        long l = aLong;
//                        int i = (int) l;
//                        return ints[i];
//                    }
//                })
//                .sample(3, TimeUnit.SECONDS)
//                .throttleFirst(3,TimeUnit.SECONDS)
//                .timeout(2, TimeUnit.SECONDS)
                .debounce(1,TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.e(">>>", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(">>>", "onError");
                    }

                    @Override
                    public void onNext(Long integer) {
                        Log.e(">>>", integer + "");
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }
}
