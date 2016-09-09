package org.bugkillers.hystrix;

import com.netflix.hystrix.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liuxinyu on 16/7/25.
 */
public class SemphoreTest {

    public static volatile AtomicInteger atomicInteger = new AtomicInteger();

    public static class SemaphoreCommand extends HystrixCommand<String> {
        public SemaphoreCommand() {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("Semaphore Echo"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("Echo"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withExecutionTimeoutInMilliseconds(1)
                            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                            .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)
                            .withCircuitBreakerErrorThresholdPercentage(50))
            );
        }

        /**
         * Implement this method with code to be executed when {@link #execute()} or {@link #queue()} are invoked.
         *
         * @return R response type
         * @throws Exception if command execution fails
         */
        @Override
        protected String run() throws Exception {
            atomicInteger.incrementAndGet();
            System.out.println("pre" + atomicInteger.intValue());
//            if (true) {
//
//                throw new RuntimeException("123");
//            }
            TimeUnit.MILLISECONDS.sleep(5);
            return "123";
        }

        @Override
        protected String getFallback() {

            if (isCircuitBreakerOpen()) {
                System.out.println("isCircuitBreakerOpen");
            }
            return "fallback";
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            try {
                System.out.println(new SemaphoreCommand().execute());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
