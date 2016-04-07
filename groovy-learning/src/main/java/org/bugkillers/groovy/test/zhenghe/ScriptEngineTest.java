package org.bugkillers.groovy.test.zhenghe;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.IOException;

/**
 * Created by liuxinyu on 16/4/7.
 */
public class ScriptEngineTest {
    public static void main(String args[]) throws ResourceException, ScriptException, IOException {
        String[] roots = new String[]{"/Users/liuxinyu/code/github/Learning-code-demo/groovy-learning/src/main/java/org/bugkillers/groovy/test/zhenghe/"};//定义Groovy脚本引擎的根路径
        GroovyScriptEngine engine = new GroovyScriptEngine(roots);
        Binding binding = new Binding();
        binding.setVariable("language", "Groovy");
        Object value = engine.run("SimpleScript.groovy", binding);
        assert value.equals("The End");

    }
}
