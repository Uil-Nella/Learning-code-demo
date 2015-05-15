package org.bugkillers.learning.guava.eventbus;

/**
 * Created by liuxinyu on 15/5/15.
 */
public class MessageEvent {

    private String message;

    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
