package org.bugkillers.hystrix.rx;

import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liuxinyu on 16/6/12.
 */
public class RxJavaTest {

    @Test
    public void ObservableTest() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello World!!!");
                        subscriber.onCompleted();
                    }
                }
        );


        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println();
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        myObservable.subscribe(mySubscriber);


    }


    @Test
    public void SimpleTest() {
        Observable<String> observable = Observable.just("Hello RxJava");

        Action1<String> subscriber = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

        observable.subscribe(subscriber);


        Observable.just("Hello Simple").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }


    @Test
    public void operatorTest() {
        Observable.just("#Basic Markdown to HTML").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                if (s != null && s.startsWith("#")) {
                    return "<h>" + s.substring(1, s.length());
                }
                return null;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    @Test
    public void multiThreadTest() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                System.out.println(Thread.currentThread().getName());
                subscriber.onNext("MultiThread");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }
}
