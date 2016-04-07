/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.zhenghe;

import groovy.util.Eval;

/**
 * Created by liuxinyu on 16/4/7.
 */
public class EvalTest {
    public static void main(String[] args) {
        System.out.println(Eval.me("33*3"));
        System.out.println(Eval.me("'foo'.toUpperCase()"));
    }
}
