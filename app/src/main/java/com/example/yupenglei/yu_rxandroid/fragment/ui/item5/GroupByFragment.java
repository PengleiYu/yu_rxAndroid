package com.example.yupenglei.yu_rxandroid.fragment.ui.item5;

import android.util.Log;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.subjects.PublishSubject;

/**
 * Created by yupenglei on 17/4/6.
 */

public class GroupByFragment extends MidLayerFragment {
    @Override
    protected void loadApps() {
        test1();
    }

    private void test3() {
        Observable.from(ApplicationList.getInstance()
                .getList())
                .toSortedList(new Func2<AppInfo, AppInfo, Integer>() {
                    @Override
                    public Integer call(AppInfo info, AppInfo info2) {
                        return info.getName().length() - info2.getName().length();
                    }
                })
                .subscribe(new Subscriber<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        doCompelet("hah");
                    }

                    @Override
                    public void onError(Throwable e) {
                        doError();
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        Log.e(">>>", "hello");
                        Log.e(">>>", "appInfos len=" + appInfos.size());
                        Observable<GroupedObservable<String, AppInfo>> groupBy = Observable
                                .from(appInfos)
                                .groupBy(new Func1<AppInfo, String>() {
                                    @Override
                                    public String call(AppInfo info) {
                                        return info.getName().charAt(0) + "";
                                    }
                                });
                        Log.e(">>>", "hello2");
                        Observable
                                .concat(groupBy)
//                                .from(appInfos)
                                .subscribe(new Subscriber<AppInfo>() {
                                    @Override
                                    public void onCompleted() {
                                        doCompelet("groupBy");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        doError();
                                    }

                                    @Override
                                    public void onNext(AppInfo info) {
                                        Log.e(">>>", "hello3");
                                        mAdapter.addAppInfo(info);
                                    }
                                });
                    }

                });
    }


    private void test1() {
        Observable<GroupedObservable<String, AppInfo>> groupedItems = Observable
                .from(ApplicationList.getInstance().getList())
                .groupBy(new Func1<AppInfo, String>() {
                    @Override
                    public String call(AppInfo info) {
                        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy", Locale.CHINA);
                        return format.format(new Date(info.getLastUpdateTime()));
                    }
                });
        Observable.concat(groupedItems)
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        doCompelet("groupBy");
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

    private void test2() {
        Observable<GroupedObservable<Integer, AppInfo>> groupedObservableObservable = Observable
                .from(ApplicationList.getInstance().getList())
                .groupBy(new Func1<AppInfo, Integer>() {
                    @Override
                    public Integer call(AppInfo info) {
                        return info.getName().length();
                    }
                })
                .toSortedList()
                .flatMap(new Func1<List<GroupedObservable<Integer, AppInfo>>,
                        Observable<GroupedObservable<Integer, AppInfo>>>() {
                    @Override
                    public Observable<GroupedObservable<Integer, AppInfo>> call
                            (List<GroupedObservable<Integer, AppInfo>> groupedObservables) {
                        return Observable.from(groupedObservables);
                    }
                });
        Observable.concat(groupedObservableObservable)
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        doCompelet("groupBy");
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
