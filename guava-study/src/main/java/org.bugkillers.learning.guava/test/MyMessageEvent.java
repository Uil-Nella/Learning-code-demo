package org.bugkillers.learning.guava.test;

/**
 * Created by liuxinyu on 15/5/15.
 */
public class MyMessageEvent {

    private String message;

    public MyMessageEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
