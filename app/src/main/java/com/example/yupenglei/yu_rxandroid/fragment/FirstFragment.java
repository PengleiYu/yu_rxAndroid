package com.example.yupenglei.yu_rxandroid.fragment;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yupenglei.yu_rxandroid.R;
import com.example.yupenglei.yu_rxandroid.Utils;
import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.AppInfoRich;
import com.example.yupenglei.yu_rxandroid.app.ApplicationAdapter;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yupenglei on 17/3/31.
 */

public class FirstFragment extends MidLayerFragment implements SwipeRefreshLayout
        .OnRefreshListener {

    private void refreshList() {
        getApps()
                .subscribeOn(Schedulers.io())
                .toSortedList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "Here is a list", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        mAdapter.addAppInfos(appInfos);
                        mSwipeRefreshLayout.setRefreshing(false);
                        storeList(appInfos);
                    }
                });
    }

    private void storeList(final List<AppInfo> appInfos) {
        ApplicationList.getInstance().setList(appInfos);

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

    private Observable<File> getFileDir() {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                subscriber.onNext(getActivity().getFilesDir());
                subscriber.onCompleted();
            }
        });
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
                    Log.e(">>>", String.format("iconPath=%s", iconPath));
                    subscriber.onNext(new AppInfo(name, iconPath, appInfoRich.getLastUpdateTime()));
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        refreshList();
    }
}
