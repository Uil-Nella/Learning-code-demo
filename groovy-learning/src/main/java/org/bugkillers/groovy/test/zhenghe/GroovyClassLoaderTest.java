package org.bugkillers.groovy.test.zhenghe;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

/**
 * Created by liuxinyu on 16/4/7.
 */
public class GroovyClassLoaderTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        GroovyClassLoader classLoader = new GroovyClassLoader();
        Class<? extends GroovyObject> clazz = classLoader.parseClass("class Foo { void doIt() { println \"ok\" } }");

        GroovyObject obj =clazz.newInstance();

        obj.invokeMethod("doIt",null);

    }
}
