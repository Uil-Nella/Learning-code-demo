/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by liuxinyu on 16/4/5.
 */
class ClassMixinTest {

    public static void main(String[] args) {


        ExecutorService service = Executors.newFixedThreadPool(100)

        while (true) {
            service.submit(new Runnable() {
                @Override
                void run() {
                    invoke()
                }
            })


        }
    }

    static void invoke() {
        ClassMixinTest.User user = new ClassMixinTest.User()

        long start = System.currentTimeMillis()

        user.metaClass.mixin(ClassMixinTest.Man)

        println("spend" + (System.currentTimeMillis() - start))

    }


    static class User {
    }

    static class Man {
    }

}
