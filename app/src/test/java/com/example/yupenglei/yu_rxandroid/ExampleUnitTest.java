package com.example.yupenglei.yu_rxandroid;

import org.junit.Test;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;

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
}