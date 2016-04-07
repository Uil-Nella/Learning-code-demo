/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta.metaclass

/**
 * Created by liuxinyu on 16/3/14.
 */

class Stuff {
    static invokeMe() { "foo" }
}

Stuff.metaClass.'static'.invokeMethod = { String name, args ->
    def metaMethod = Stuff.metaClass.getStaticMetaMethod(name, args)
    def result
    if(metaMethod) result = metaMethod.invoke(delegate,args)
    else {
        result = "bar"
    }
    result
}

assert "foo" == Stuff.invokeMe()
assert "bar" == Stuff.doStuff()

//扩展接口

List.metaClass.sizeDoubled = {-> delegate.size()*2 }

def list = []

list << 1
list << 3

assert 4==list.sizeDoubled();