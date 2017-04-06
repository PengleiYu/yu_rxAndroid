package com.example.yupenglei.yu_rxandroid.fragment.ui.item4;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by yupenglei on 17/4/5.
 */

public class TakeFragment extends MidLayerFragment {
    @Override
    public void onRefresh() {
        loadList(ApplicationList.getInstance().getList());
    }

    private void loadList(List<AppInfo> appInfos) {
        Observable.from(appInfos)
                .take(4)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAdapter.clear();
                    }
                })
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        doCompelet("take");
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
