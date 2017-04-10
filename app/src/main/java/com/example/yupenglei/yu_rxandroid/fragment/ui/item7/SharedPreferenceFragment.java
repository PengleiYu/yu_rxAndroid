package com.example.yupenglei.yu_rxandroid.fragment.ui.item7;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by yupenglei on 17/4/10.
 */

public class SharedPreferenceFragment extends MidLayerFragment {
    @Override
    protected Observable<AppInfo> getObservable() {
        return Observable.create(new Observable.OnSubscribe<List<AppInfo>>() {
            @Override
            public void call(Subscriber<? super List<AppInfo>> subscriber) {
                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
                Type type = new TypeToken<List<AppInfo>>() {
                }.getType();
                String apps = sp.getString("Apps", null);
                if (!TextUtils.isEmpty(apps)) {
                    List<AppInfo> appInfos = new Gson().fromJson(apps, type);
                    subscriber.onNext(appInfos);
                }
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<List<AppInfo>, Observable<AppInfo>>() {
            @Override
            public Observable<AppInfo> call(List<AppInfo> appInfos) {
                return Observable.from(appInfos);
            }
        }).onBackpressureBuffer();
    }
}
