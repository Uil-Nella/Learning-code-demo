/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.ast

/**
 * Created by liuxinyu on 16/1/4.
 */
class AstTest {
    static def main(args){
        new Person();


        def parent = AstTest.classLoader;

        def loader = new GroovyClassLoader(parent)

        def gclass = loader.parseClass(new File("/Users/liuxinyu/code/git/groovyTest/src/com/meituan/hotel/groovy/test/ast/Person.groovy"))

    }
}
