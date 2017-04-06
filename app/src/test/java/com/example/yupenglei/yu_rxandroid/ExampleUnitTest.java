package com.example.yupenglei.yu_rxandroid;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDefer() {
        SomeType someType = new SomeType();
        Observable<String> observable = someType.valueObservable();
        someType.setValue("hello");
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    @Test
    public void testRange() {
        Observable.range(10, 3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    public class SomeType {
        private String mValue;

        public void setValue(String value) {
            mValue = value;
        }

        public Observable<String> valueObservable() {
            return Observable.defer(new Func0<Observable<String>>() {
                @Override
                public Observable<String> call() {
                    return Observable.just(mValue).repeat(5);
                }
            });
        }
    }

    @Test
    public void testDistinctUntilChange() {
        Observable.just(21, 22, 22, 23, 22, 24, 24, 24, 22)
//                .distinctUntilChanged()
//                .skipLast(1)
//                .elementAt(3)
//                .sample(1, TimeUnit.SECONDS)
//                .elementAtOrDefault(11,-1)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });

    }

    @Test
    public void testSampling() {
        final int[] ints = new int[]{21, 22, 22, 23, 22, 24, 24, 24, 22};

        Observable.interval(2, TimeUnit.SECONDS)
//                .map(new Func1<Long, Integer>() {
//                    @Override
//                    public Integer call(Long aLong) {
//                        long l = aLong;
//                        return ints[(int) l];
//                    }
//                })
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("complete");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("error");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
                .sample(4, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("complete");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println(aLong);
                    }
                });


    }
}