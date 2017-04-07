package com.example.yupenglei.yu_rxandroid.fragment.ui.item6;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

/**
 * Created by yupenglei on 17/4/6.
 */

public class MergeFragment extends MidLayerFragment {
    @Override
    protected Observable<AppInfo> getObservable() {
        List<AppInfo> list1 = ApplicationList.getInstance().getList();
        Observable<AppInfo> observable1 = Observable.from(list1);
        List<AppInfo> list2 = new ArrayList<>(list1);
        Collections.reverse(list2);
        Observable<AppInfo> observable2 = Observable.from(list2);
//        return Observable.merge(observable1, observable2);
        return Observable.mergeDelayError(observable1, observable2);
    }
}
