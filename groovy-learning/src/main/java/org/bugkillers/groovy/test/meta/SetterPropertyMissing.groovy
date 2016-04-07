/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta

/**
 * Created by liuxinyu on 16/3/14.
 */
class Foo2 {
    def storage = [:]
    def propertyMissing(String name, value) { storage[name] = value }
    def propertyMissing(String name) { storage[name] }
}

def f = new Foo2()
f.foo = "bar"

assert f.foo == "bar"