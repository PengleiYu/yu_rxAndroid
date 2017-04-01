package com.example.yupenglei.yu_rxandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.AppInfoRich;
import com.example.yupenglei.yu_rxandroid.app.ApplicationAdapter;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by yupenglei on 17/3/31.
 */

public class FirstFragment extends ExampleFragment {
    @BindView(R.id.recycler_fragment_example)
    RecyclerView mRecyclerView;

    private File mFileDir;
    private Unbinder mUnbinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(getActivity(), view);


        ApplicationAdapter adapter=new ApplicationAdapter(new ArrayList<AppInfo>());
        mRecyclerView.setAdapter(adapter);

//        getFileDir()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<File>() {
//                    @Override
//                    public void call(File file) {
//                        mFileDir = file;
//                        refreshList();
//                    }
//                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void refreshList() {

    }

    private void storeList(final List<AppInfo> appInfos) {
        ApplicationList.getInstance().setList(appInfos);

        Schedulers.io().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
                Type type = new TypeToken<List<AppInfo>>() {
                }.getType();
                sp.edit().putString("Apps", new Gson().toJson(appInfos, type)).apply();
            }
        });
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
                    String iconPath = mFileDir + File.pathSeparator + name;
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

}
