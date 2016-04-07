package org.bugkillers.groovy.test.zhenghe;

import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * Created by liuxinyu on 16/4/7.
 */
public class GroovyShellTest {
    public static void main(String[] args) {
        GroovyShell shell = new GroovyShell();

        System.out.println(shell.evaluate("3*5"));

        Script script = shell.parse("3*5");

        System.out.println(script.run());
    }
}
