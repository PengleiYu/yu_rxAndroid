package com.example.yupenglei.yu_rxandroid.fragment.ui.item5;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import rx.Observable;
import rx.functions.Func2;

/**
 * Created by yupenglei on 17/4/6.
 */

public class ScanFragment extends MidLayerFragment {
    @Override
    protected Observable<AppInfo> getObservable() {
        return Observable.from(ApplicationList.getInstance().getList())
                .scan(new Func2<AppInfo, AppInfo, AppInfo>() {
                    @Override
                    public AppInfo call(AppInfo info, AppInfo info2) {
                        return info.getName().length() > info2.getName().length() ? info : info2;
                    }
                })
                .distinct();
    }
}
