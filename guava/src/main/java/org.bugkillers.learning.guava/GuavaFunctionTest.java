/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.learning.guava;


import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Created by liuxinyu on 15/4/7.
 */
public class GuavaFunctionTest {
    public static void main(String[] args) {

        Function<String, Integer> lengthFunction = new Function<String, Integer>() {
            public Integer apply(String string) {
                return string.length();
            }
        };
        Predicate<String> allCaps = new Predicate<String>() {
            public boolean apply(String string) {
                return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string);
            }
        };


    }
}
