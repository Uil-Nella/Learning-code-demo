package org.bugkillers.spring.test;

import org.bugkillers.spring.method.ICalculate;
import org.bugkillers.spring.util.SpringContextUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuxinyu on 15/5/5.
 */
public class CalculateTest extends SpringContextUtil {

    @Autowired
    private ICalculate calculate;

    @Test
    public void addTest(){
        int sum = calculate.plus(1,1);
        System.out.println(sum);
    }


}
