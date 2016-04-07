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
class GORM {

    def dynamicMethods = [] // 一些利用正则表达式的动态方法

    def methodMissing(String name, args) {
        def method = dynamicMethods.find { it.match(name) }
        if(method) {
            GORM.metaClass."$name" = { Object[] varArgs ->
                method.invoke(delegate, name, varArgs)
            }
            return method.invoke(delegate,name, args)
        }
        else throw new MissingMethodException(name, delegate, args)
    }
}