/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.learning.guava;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 空值判断
 * Created by liuxinyu on 15/4/14.
 */
public class NullAssertTest {
    public static void main(String[] args) {
        String str = "";
        Object obj = null;
        List list = null;
        Map map = null;


        System.out.println(Objects.equal(obj,null));
        System.out.println(Objects.equal(str,null));


        list = Lists.newArrayList();
        map = Maps.newHashMap();


        System.out.println();
        System.out.println();



    }

}
