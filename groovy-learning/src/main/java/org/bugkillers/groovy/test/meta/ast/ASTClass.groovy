/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta.ast

import groovy.transform.Memoized

/**
 * Created by liuxinyu on 16/3/14.
 */


//利用 @Delegate 注释 when 字段，意味着 Event 类将把对 Date 方法的所有调用都委托给 when 字段

class Event {
    @Delegate
    Date when
    String title
}

def ev = new Event(title:'Groovy keynote', when: Date.parse('yyyy/MM/dd', '2013/09/10'))
def now = new Date()
assert ev.before(now)


//缓存结果
@Memoized
long longComputation(int seed) {
    // 延缓计算
    Thread.sleep(1000*seed)
    System.nanoTime()
}

def x = longComputation(1) // 1 秒后返回结果
def y = longComputation(1) // 立刻返回结果
def z = longComputation(2) // 2 秒后返回结果
assert x==y
assert x!=z

//单例
@Singleton
class GreetingService {
    String greeting(String name) { "Hello, $name!" }
}
assert GreetingService.instance.greeting('Bob') == 'Hello, Bob!'

class Collaborator {
    public static boolean init = false
}
@Singleton(lazy=true,strict=false)
class GreetingService2 {
    static void init() {}
    GreetingService2() {
        Collaborator.init = true
    }
    String greeting(String name) { "Hello, $name!" }
}
GreetingService2.init() // 确保类被初始化
assert Collaborator.init == false
GreetingService2.instance
assert Collaborator.init == true
assert GreetingService2.instance.greeting('Bob') == 'Hello, Bob!'