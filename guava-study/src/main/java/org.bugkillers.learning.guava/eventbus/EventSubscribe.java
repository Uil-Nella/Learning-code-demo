package org.bugkillers.learning.guava.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * 消息订阅者
 * 单个事件
 * Created by liuxinyu on 15/5/15.
 */
public class EventSubscribe {

    @Subscribe
    public void listen(MessageEvent event){
        String message = event.getMessage();
        System.out.println("接受到消息："+message);
    }
}
