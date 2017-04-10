package com.example.yupenglei.yu_rxandroid.fragment.ui.item1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.yupenglei.yu_rxandroid.Utils;
import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.AppInfoRich;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yupenglei on 17/3/31.
 */

public class FirstFragment extends MidLayerFragment {

    private void storeList(List<AppInfo> appInfos) {
        ApplicationList.getInstance().setList(appInfos);

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Type type = new TypeToken<List<AppInfo>>() {
        }.getType();
        sharedPreferences.edit().putString("Apps", new Gson().toJson(appInfos, type)).apply();
//        Schedulers.io().createWorker().schedule(new Action0() {
//            @Override
//            public void call() {
//                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
//                Type type = new TypeToken<List<AppInfo>>() {
//                }.getType();
//                sp.edit().putString("Apps", new Gson().toJson(appInfos, type)).apply();
//            }
//        });
    }

    private Observable<AppInfo> getApps() {
        return Observable.create(new Observable.OnSubscribe<AppInfo>() {
            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {
                List<AppInfoRich> appInfos = new ArrayList<>();

                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

                List<ResolveInfo> resolveInfos = getActivity().getPackageManager()
                        .queryIntentActivities(mainIntent, 0);
                for (ResolveInfo info : resolveInfos) {
                    appInfos.add(new AppInfoRich(getActivity(), info));
                }
                for (AppInfoRich appInfoRich : appInfos) {
                    Bitmap icon = Utils.drawable2Bitmap(appInfoRich.getIcon());
                    String name = appInfoRich.getName();
                    String iconPath = getActivity().getFilesDir() + "/" + name;
                    Utils.storeBitmap(getActivity(), icon, name);

                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onNext(new AppInfo(name, iconPath, appInfoRich.getLastUpdateTime()));
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });

    }

    @Override
    protected Observable<AppInfo> getObservable() {
        Observable<List<AppInfo>> listObservable = getApps().subscribeOn(Schedulers.io())
                .toSortedList();
        listObservable.subscribe(new Subscriber<List<AppInfo>>() {
            @Override
            public void onCompleted() {
                Log.e(">>>", "storeList completed");
            }

            @Override
            public void onError(Throwable e) {

                Log.e(">>>", "storeList error");
            }

            @Override
            public void onNext(List<AppInfo> appInfos) {
                storeList(appInfos);
            }
        });

        Observable<Observable<AppInfo>> map = listObservable
                .map(new Func1<List<AppInfo>, Observable<AppInfo>>() {
                    @Override
                    public Observable<AppInfo> call(List<AppInfo> appInfos) {
                        return Observable.from(appInfos);
                    }
                });
        return Observable.concat(map).observeOn(AndroidSchedulers.mainThread());
    }

}
