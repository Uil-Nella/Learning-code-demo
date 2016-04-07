/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta.test

import groovy.transform.ASTTest
import groovy.transform.PackageScope
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.stmt.Statement

import static org.codehaus.groovy.control.CompilePhase.*

/**
 * Created by liuxinyu on 16/3/14.
 */
@ASTTest(phase=CONVERSION, value={        //       1⃣️
    assert node instanceof ClassNode  //           2⃣️
    assert node.name == 'Person'        //         3⃣️
})
class Person {

}

@ASTTest(phase=SEMANTIC_ANALYSIS, value= {
    def nameNode = node.properties.find { it.name == 'name' }
    def ageNode = node.properties.find { it.name == 'age' }
    assert nameNode
    assert ageNode == null // 再也不应该是一个属性了
    def ageField = node.getDeclaredField 'age'
    assert ageField.modifiers == 0
})
class Person1 {
    String name
    @PackageScope int age
}

def list = lookup('anchor')// 1⃣️
Statement stmt = list[0] //2⃣️