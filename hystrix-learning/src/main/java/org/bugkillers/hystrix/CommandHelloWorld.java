package org.bugkillers.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * Created by liuxinyu on 16/6/9.
 */
public class CommandHelloWorld extends HystrixCommand<String> {


    private final String name;

    @Override
    protected String run() throws Exception {
        return "Hello" + name;
    }


    public CommandHelloWorld(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")));
        this.name = name;
    }
}
