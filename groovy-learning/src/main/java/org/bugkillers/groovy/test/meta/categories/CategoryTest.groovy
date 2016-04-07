/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta.categories

/**
 * Created by liuxinyu on 16/3/14.
 */

class Distance{
    def number;

    String toString(){
        "$number m"
    }
}



@Category(Number)
class NumberCategory{
    Distance getMeters(){
        new Distance(number: this)
    }
}



use(NumberCategory){
    assert 42.meters.toString() == "42 m"


}