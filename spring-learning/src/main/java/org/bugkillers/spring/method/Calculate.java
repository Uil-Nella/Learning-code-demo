package org.bugkillers.spring.method;

import org.springframework.stereotype.Component;

/**
 * Created by liuxinyu on 15/5/5.
 */
@Component
public class Calculate implements ICalculate {
    /**
     * 計算a + b 的和
     *
     * @param a
     * @param b
     * @return
     */
    @Override
    public int plus(int a, int b) {
        return a + b;
    }
}
