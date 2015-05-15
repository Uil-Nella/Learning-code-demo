package org.bugkillers.learning.guava;

import com.google.common.eventbus.EventBus;

/**
 * Created by liuxinyu on 15/4/15.
 */
public class EventBusTest {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new Event());
        eventBus.post("ssss");
    }
}
