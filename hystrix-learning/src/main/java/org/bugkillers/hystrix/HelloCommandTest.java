package org.bugkillers.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by liuxinyu on 16/6/9.
 */
public class HelloCommandTest {

    @Test
    public void testHello() throws ExecutionException, InterruptedException {
        String str = new CommandHelloWorld("bob").execute();

        System.out.println(str);

        Future<String> future = new CommandHelloWorld("Bob").queue();

        System.out.println(future.get());

        Observable<String> observable = new CommandHelloWorld("Bob").observe();


        observable.subscribe();

    }


    @Test
    public void testObservable(){
        Observable<String> future = new ObservableCommandHello("World").observe();

        future.subscribe(new Action1<String>() {
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    @Test
    public void testObservable2() throws Exception {

        Observable<String> fWorld = new ObservableCommandHello("World").observe();
        Observable<String> fBob = new ObservableCommandHello("Bob").observe();

        // blocking
        Assert.assertEquals("Hello World!", fWorld.toBlocking().single());
        Assert.assertEquals("Hello Bob!", fBob.toBlocking().single());

        // non-blocking
        // - this is a verbose anonymous inner-class approach and doesn't do assertions
        fWorld.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                // nothing needed here
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }

        });

        // non-blocking
        // - also verbose anonymous inner-class
        // - ignore errors and onCompleted signal
        fBob.subscribe(new Action1<String>() {

            @Override
            public void call(String v) {
                System.out.println("onNext: " + v);
            }

        });
    }

    @Test
    public void testSynchronous() {
        Assert.assertEquals("Hello Failure World!", new CommandHelloFailure("World").execute());
        Assert.assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").execute());
    }


    @Test
    public void testWithoutCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Assert.assertTrue(new CommandUsingRequestCache(2).execute());
            Assert.assertFalse(new CommandUsingRequestCache(1).execute());
            Assert.assertTrue(new CommandUsingRequestCache(0).execute());
            Assert.assertTrue(new CommandUsingRequestCache(58672).execute());
        } finally {
            context.shutdown();
        }
    }

}
