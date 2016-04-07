/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.oom;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovySystem;

import java.io.File;
import java.io.IOException;

/**
 * Created by liuxinyu on 16/3/28.
 */
public class OOMTest {

    public static void main(String[] args) throws IOException, InterruptedException, IllegalAccessException, InstantiationException {
        while (true) {
            String path = "/Users/liuxinyu/code/git/groovyTest/src/com/meituan/hotel/groovy/test/oom/Hello.groovy";
            GroovyClassLoader classLoader = new GroovyClassLoader(ClassLoader.getSystemClassLoader());
            Class helloClass = classLoader.parseClass(new File(path));
//            Hello instance = (Hello) helloClass.newInstance();
//            instance.say();
            GroovySystem.getMetaClassRegistry().removeMetaClass(helloClass);
            Thread.sleep(10);
        }
    }

}
