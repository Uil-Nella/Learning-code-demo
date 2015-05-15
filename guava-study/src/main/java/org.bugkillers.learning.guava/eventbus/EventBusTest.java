package org.bugkillers.learning.guava.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * Created by liuxinyu on 15/5/15.
 */
public class EventBusTest {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus("test");
        EventSubscribe subscribe = new EventSubscribe();
        eventBus.register(subscribe);

        eventBus.post(new MessageEvent("哈哈"));
        eventBus.post(new MessageEvent("嘿嘿"));
        eventBus.post(new MessageEvent("嘻嘻"));
        eventBus.post(new MessageEvent("嘎嘎"));



        MultipleListener listener = new MultipleListener();
        eventBus.register(listener);
        eventBus.post(new Integer(11));
        eventBus.post(new Integer(22));
        eventBus.post(new Integer(33));
        eventBus.post(new Long(44));
        eventBus.post(new Long(55));
        eventBus.post(new Long(66));




    }

}
