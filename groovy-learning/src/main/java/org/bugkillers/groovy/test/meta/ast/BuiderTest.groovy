/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta.ast

import groovy.transform.Immutable
import groovy.transform.ToString
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

/**
 * Created by liuxinyu on 16/3/14.
 */

@ToString
@Builder(builderStrategy=SimpleStrategy)//链式setter
class Person4{
    String first
    String last
    Integer born
}

def p1 = new Person4().setFirst("hha").setLast("last").setBorn(123)

println(p1)


//不可变
@Builder(builderStrategy=SimpleStrategy)
@Immutable
class Person5{
    String name
    String age
}

def p5 = new Person5().setName("123123").setAge("w32")
//无效操作
//p5.setName("sdfsd")
println(p5)


