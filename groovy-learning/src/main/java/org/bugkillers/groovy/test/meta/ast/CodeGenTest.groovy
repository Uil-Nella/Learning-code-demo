/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta.ast

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.IndexedProperty
import groovy.transform.InheritConstructors
import groovy.transform.ToString
import groovy.transform.TupleConstructor
//import groovy.transform.Sortable

/**
 * Created by liuxinyu on 16/3/14.
 */
/**
 * 生成构造器
 */
@TupleConstructor
/**
 * toString
 */
@ToString
@EqualsAndHashCode
class Person {
    String firstName

    String lastName
}


def person = new Person(firstName: "lalala",lastName: "hahaha")

println(person)

println(person.hashCode())

def person2 = new Person("22","33")
println(person2)

/**
 * 具有三个效果
 */
@Canonical
class Person2 {
    String firstName

    String lastName
}

/**
 * 生成父类的构造方法
 */
@InheritConstructors
class CustomException extends Exception {}

// 所有这些都生成构造函数
new CustomException()
new CustomException("A custom message")
new CustomException("A custom message", new RuntimeException())
new CustomException(new RuntimeException())

// Java 7 only
// new CustomException("A custom message", new RuntimeException(), false, true)

/**
 * 索引化属性
 */
class SomeBean {
    @IndexedProperty String[] someArray = new String[2]
    @IndexedProperty List someList = []
}

def bean = new SomeBean()
bean.setSomeArray(0, 'value')
bean.setSomeList(0, 123)

assert bean.someArray[0] == 'value'
assert bean.someList == [123]

/**
 * 延迟加载
 */
class SomeBean1 {
    @Lazy LinkedList myField
}


//@Sortable
class Person3 {
    String first
    String last
    Integer born
}


