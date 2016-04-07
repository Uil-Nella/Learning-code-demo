/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test

/**
 * Created by liuxinyu on 16/4/6.
 */
class ClassMixin {
    public static void main(String[] args) {
        String ss = "";

        ss.metaClass.mixin(A)


        ss.method1()

        ss.method2()

        ss.sMethod()

        println(ss.getAa())
    }


    static class A extends SA {
        String aa = "aa"

        static void method1(){
            println("method1")
        }

        void method2(){
            println("method1")

        }
    }

    static class SA{
        void  sMethod(){
            println("s method")
        }
    }


}
