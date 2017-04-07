package com.example.yupenglei.yu_rxandroid.fragment.ui.item4;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by yupenglei on 17/4/5.
 */

public class FilterFragment extends MidLayerFragment {

    @Override
    protected Observable<AppInfo> getObservable() {
        return Observable.from(ApplicationList.getInstance().getList())
                .filter(new Func1<AppInfo, Boolean>() {
                    @Override
                    public Boolean call(AppInfo info) {
                        return info.getName().toUpperCase().startsWith("C");
                    }
                });

    }
}
