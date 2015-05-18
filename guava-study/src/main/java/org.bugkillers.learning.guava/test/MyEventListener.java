package org.bugkillers.learning.guava.test;

/**
 * Created by liuxinyu on 15/5/18.
 */
public class MyEventListener {
    public void listen(MyMessageEvent event){
        String message = event.getMessage();
        System.out.println("接受到消息："+message);
    }


}
