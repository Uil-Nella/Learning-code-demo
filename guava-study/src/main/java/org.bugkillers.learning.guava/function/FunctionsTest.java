package org.bugkillers.learning.guava.function;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxinyu on 16/8/19.
 */
public class FunctionsTest {


    @Test
    public void functionsTest() {

        Function<String, Integer> functionA = new Function<String, Integer>() {
            public Integer apply(String input) {
                return Integer.parseInt(input);
            }
        };

        Function<Integer, Long> functionB = new Function<Integer, Long>() {
            public Long apply(Integer input) {
                return input.longValue();
            }
        };

        //组合
        Function<String, Long> functionC = Functions.compose(functionB, functionA);


        List<Long> longs = Lists.transform(Lists.newArrayList("1", "2", "4"), functionC);

        System.out.println(longs);

    }


    @Test
    public void functionTest02() {

        //将任意对象转换成
        Function<Object, Integer> function = Functions.constant(1);

        List<Integer> list = Lists.transform(Lists.newArrayList("111", 213, 3434L, new Object()), function);

        System.out.println(list);

    }

    @Test
    public void functionTest03() {

        String sms = "Hello, {name}. Welcome to {place}. Show starts at {time}.";

        String patternStr = "(\\{([^\\}]+)})";
        Matcher matcher = Pattern.compile(patternStr).matcher(sms);


        StringBuffer result = new StringBuffer();
        Function<String, String> lookup = Functions.forMap(ImmutableMap.of("name", "allen", "place", "beijing"), "");
        while (matcher.find()) {
            matcher.appendReplacement(result, lookup.apply(matcher.group(2)));
        }
        matcher.appendTail(result);

        System.out.println(result.toString());

    }


    @Test
    public void functionTest04() {
        //返回对象本身
        Functions.identity();
    }

    @Test
    public void functionTest05() {

        //Predicate 转function
        Function<String, Boolean> function = Functions.forPredicate(new Predicate<String>() {
            public boolean apply(String input) {
                return Strings.isNullOrEmpty(input);
            }
        });

        List<Boolean> list = Lists.transform(Lists.newArrayList("123","3123","",""),function);

        System.out.println(list);
    }


}
