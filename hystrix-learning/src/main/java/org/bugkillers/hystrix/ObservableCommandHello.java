package org.bugkillers.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by liuxinyu on 16/6/9.
 */
public class ObservableCommandHello extends HystrixObservableCommand<String> {

    private final String name;

    public ObservableCommandHello(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {


        return Observable.create(new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    subscriber.onNext("Hello");
                    subscriber.onNext(name + " !");

                    subscriber.onCompleted();
                }
            }
        });
    }


}
