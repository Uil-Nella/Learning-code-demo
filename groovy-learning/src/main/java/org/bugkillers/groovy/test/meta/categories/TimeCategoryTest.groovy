/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta.categories

import groovy.time.TimeCategory

/**
 * Created by liuxinyu on 16/3/14.
 */

use(TimeCategory){
    println(1.minute.from.now)

    println(10.hours.ago)

    def someDate = new Date();

    println(someDate - 3.months)
}
