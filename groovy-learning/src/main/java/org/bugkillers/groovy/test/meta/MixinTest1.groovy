package org.bugkillers.groovy.test.meta
/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.

/**
 * Created by liuxinyu on 16/4/1.
 */


def main() {
    StringBuffer sb = new StringBuffer("213");

    sb.metaClass.mixin(HAHA)

    sb.say()
}

def test() {
    StringBuffer sb = new StringBuffer("213");

    sb.metaClass.mixin(HAHA)

    sb.say()
}





