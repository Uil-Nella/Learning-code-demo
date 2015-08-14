package org.bugkillers.learning.guava.function;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


/**
 * Created by liuxinyu on 15/8/14.
 */
public class FunctionTest {

    public static Map testMap = null;

    static{
        testMap = Maps.newHashMap();
        testMap.put(1,"java");
        testMap.put(2,"shell");
        testMap.put(3,"sql");
    }


    @Test
    public void FunctionTest01(){
        Function<String,Integer> lengthFuction = new Function<String, Integer>() {
            public Integer apply(String s) {
                return s.length();
            }
        };

        Predicate<String> allCaps = new Predicate<String>() {
            public boolean apply(String s) {
                return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(s);
            }
        };


//        Multiset<Integer> lengths = HashMultimap.create(
//                Iterables.transform(Iterables.filter(null,allCaps),lengthFuction)
//        );
    }

    @Test
    public void forMapTest01(){
        Function<Integer,String> function = Functions.forMap(testMap);
        Assert.assertTrue(true);
    }

}
