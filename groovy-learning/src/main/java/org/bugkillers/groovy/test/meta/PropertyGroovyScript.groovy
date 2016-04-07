/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta

import org.codehaus.groovy.runtime.metaclass.OwnedMetaClass

/**
 * Created by liuxinyu on 16/3/14.
 */

def someGroovyClass = new PropertyGroovy()

assert someGroovyClass.field1 == 'getHa'
assert someGroovyClass.field2 == 'ho'
assert someGroovyClass.field3 == 'field3'
assert someGroovyClass.field4 == 'hu'


someGroovyClass.metaClass


//自定义
someGroovyClass.metaClass = new OwnedMetaClass() {
    @Override
    protected Object getOwner() {
        return null
    }

    @Override
    protected MetaClass getOwnerMetaClass(Object owner) {
        return null
    }
}