package com.example.yupenglei.yu_rxandroid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

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

    @Test
    public void testMerge() {
        Observable<Integer> observable1 = Observable.range(0, 10).subscribeOn(Schedulers
                .newThread());
        Observable<Integer> observable2 = Observable.range(0, 10).subscribeOn(Schedulers
                .newThread());
        Observable.merge(observable1, observable2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    @Test
    public void testMy() {
        Observable<GroupedObservable<Character, String>> groupedObservableObservable =
                Observable
                        .from(getStringList())
                        .toSortedList(new Func2<String, String, Integer>() {
                            @Override
                            public Integer call(String s, String s2) {
                                return s.length() - s2.length();
                            }
                        })
                        .flatMap(new Func1<List<String>, Observable<String>>() {
                            @Override
                            public Observable<String> call(List<String> strings) {
                                return Observable.from(strings);
                            }
                        })
                        .groupBy(new Func1<String, Character>() {
                            @Override
                            public Character call(String s) {
                                return s.charAt(0);
                            }
                        });

        Observable.concat(groupedObservableObservable)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });

    }

    private List<String> getStringList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        list.add("aaaaa");
        list.add("bbbbb");
        list.add("ccccc");
        Collections.shuffle(list);
        return list;
    }

    @Test
    public void testJoin() {
        getTargetObservable("left")
                .subscribeOn(Schedulers.io())
//        Observable.just("right1", "right2", "right3")
                .join(getTargetObservable("right").subscribeOn(Schedulers.computation()),
                        new Func1<String, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(String s) {
                                return Observable.timer(5, TimeUnit.SECONDS);
                            }
                        },
                        new Func1<String, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(String s) {
                                return Observable.timer(1, TimeUnit.SECONDS);
                            }
                        },
                        new Func2<String, String, String>() {
                            @Override
                            public String call(String s, String s2) {
                                return s + " " + s2;
                            }
                        })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    private Observable<String> getTargetObservable(final String lefOrRight) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 9; i++) {
                    subscriber.onNext(lefOrRight + "-" + i);
                    try {
                        System.out.println(lefOrRight + " sleep");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        });
//        return Observable.zip(
//                Observable.range(0, 10),
//                Observable.interval(1, TimeUnit.SECONDS),
//                new Func2<Integer, Long, String>() {
//                    @Override
//                    public String call(Integer integer, Long aLong) {
//                        return integer.toString();
//                    }
//                });
    }

    @Test
    public void testJoin2() {
        Observable<String> observableLeft = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong.toString();
                    }
                });
        Observable<Long> observableRight = Observable.interval(1000, TimeUnit.MILLISECONDS);
        observableLeft.join(observableRight,
                new Func1<String, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(String s) {
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                }, new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long aLong) {
                        return Observable.interval(0, TimeUnit.SECONDS);
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

    @Test
    public void testJoin3() {
    }

    @Test
    public void testCombineLatest() {
        Observable<Long> interval1 = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> interval2 = Observable.interval(1500, TimeUnit.MILLISECONDS);
        Observable.combineLatest(interval1, interval2,
                new Func2<Long, Long, String>() {
                    @Override
                    public String call(Long aLong, Long aLong2) {
                        System.out.println("hah");
                        return String.format("%s-%s", aLong, aLong2);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }
    @Test
    public void testStartWith(){
        Observable<String> observable = Observable.interval(1, TimeUnit.SECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return "time " + aLong;
                    }
                });
        observable
//                .startWith("hello","world","!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
        Observable.interval(1,TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println(aLong);
                    }
                });
    }
}
