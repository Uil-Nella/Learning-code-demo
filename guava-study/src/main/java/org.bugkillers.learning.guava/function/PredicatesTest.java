package org.bugkillers.learning.guava.function;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.junit.Test;


/**
 * 断言
 * Created by liuxinyu on 15/8/14.
 */
public class PredicatesTest {

    @Test
    public void predicatesTest(){
        String testStr = "Hello world";

        boolean b1 = Predicates.instanceOf(String.class).apply(testStr);
        boolean b2 = Predicates.instanceOf(String.class).equals(testStr);
        System.out.println(b1);
        System.out.println(b2);
    }





}
