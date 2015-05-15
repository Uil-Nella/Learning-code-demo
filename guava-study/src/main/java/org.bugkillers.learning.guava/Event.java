package org.bugkillers.learning.guava;

import com.google.common.eventbus.Subscribe;

/**
 * Created by liuxinyu on 15/4/15.
 */
public class Event {
    @Subscribe
    public void sub(String message){
        System.out.println(message);
    }
}
