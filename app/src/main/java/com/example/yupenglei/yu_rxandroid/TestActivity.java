package com.example.yupenglei.yu_rxandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by yupenglei on 17/4/7.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Subscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (mSubscription != null && !mSubscription.isUnsubscribed()) {
                    mSubscription.unsubscribe();
                }
                mSubscription = testStartWith();
                break;
            case R.id.stop:
                if (mSubscription != null && !mSubscription.isUnsubscribed()) {
                    mSubscription.unsubscribe();
                }
                break;
        }
    }

    public Subscription testCombineLatest() {
        Observable<Long> interval1 = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> interval2 = Observable.interval(3, TimeUnit.SECONDS);
        return Observable.combineLatest(interval1, interval2,
                new Func2<Long, Long, String>() {
                    @Override
                    public String call(Long aLong, Long aLong2) {
                        return String.format("left=%s  right=%s", aLong, aLong2);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    public Subscription testJoin2() {
        Observable<String> observableLeft = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong.toString();
                    }
                });
        Observable<Long> observableRight = Observable.interval(2000, TimeUnit.MILLISECONDS);
        return observableLeft.join(observableRight,
                new Func1<String, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(String s) {
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                }, new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long aLong) {
                        return Observable.interval(3, TimeUnit.SECONDS);
                    }
                }, new Func2<String, Long, String>() {
                    @Override
                    public String call(String s, Long aLong) {
                        return s + " " + aLong;
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    /**
     * failed
     */
    public Subscription testSwitch() {
        Observable<String> observable1 = Observable.interval(1, TimeUnit.SECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return "" + (char) ((int) 'A' + aLong.intValue());
                    }
                })
                .subscribeOn(Schedulers.newThread());
        Observable<String> observable2 = Observable.interval(5, 2, TimeUnit.SECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong.toString();
                    }
                })
                .subscribeOn(Schedulers.newThread());

        Observable<Observable<String>> just = Observable.just(observable1, observable2);

        return Observable.switchOnNext(just)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    public Subscription testStartWith() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .startWith(0L, 0L, 0L)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println(aLong);
                    }
                });
    }
}
