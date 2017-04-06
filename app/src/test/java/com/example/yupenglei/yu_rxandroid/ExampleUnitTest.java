package com.example.yupenglei.yu_rxandroid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
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

    @Test
    public void testMap() {
        getObservable()
                .switchMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
//                .flatMapIterable(new Func1<Student, Iterable<String>>() {
//                    @Override
//                    public Iterable<String> call(Student student) {
//                        return student.courses;
//                    }
//                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    private Observable<Student> getObservable() {
        return Observable.just(new Student(), new Student(), new Student());
    }

    private class Student {
        @Getter
        String name;
        @Getter
        List<String> courses;

        public Student() {
            name = "student";
            courses = new ArrayList<>();
            courses.add("a");
            courses.add("b");
            courses.add("c");
            courses.add("d");
            courses.add("e");
        }
    }


    @Test
    public void testScan() {
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    @Test
    public void testScan2() {
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .scan(10, new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    @Test
    public void testBuffer() {
        Observable.from(new Integer[]{2, 3, 4, 5, 6, 7, 8, 9,})
                .buffer(3)
//                .buffer(1,TimeUnit.SECONDS)
//                .buffer(3,2)
//                .buffer(new Func0<Observable<?>>() {
//                    @Override
//                    public Observable<?> call() {
//                        return Observable.just(10,11);
//                    }
//                })
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        System.out.println(integers);
                    }
                });
    }


    @Test
    public void testWindow() {
        Observable<Observable<Integer>> window = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6,
                7, 8, 9})
//                .window(3)
                .window(3, 4);
        Observable.concat(window)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    @Test
    public void testCast() {
        Observable.from(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9})
                .cast(Object.class)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        System.out.println(o);
                    }
                });
    }

    @Test
    public void testCast2() {
        Observable.from(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9})
                .map(new Func1<Integer, Object>() {
                    @Override
                    public Object call(Integer integer) {
                        return integer;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        System.out.println(o);
                    }
                });
    }
}