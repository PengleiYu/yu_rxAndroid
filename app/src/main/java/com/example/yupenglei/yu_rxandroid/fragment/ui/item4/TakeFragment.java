package com.example.yupenglei.yu_rxandroid.fragment.ui.item4;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import rx.Observable;

/**
 * Created by yupenglei on 17/4/5.
 */

public class TakeFragment extends MidLayerFragment {

    @Override
    protected Observable<AppInfo> getObservable() {
        return Observable.from(ApplicationList.getInstance().getList())
                .take(4);
    }
}
