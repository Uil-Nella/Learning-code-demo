package org.bugkillers.learning.guava.function;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 惰性求值
 * Created by liuxinyu on 16/8/22.
 */
public class SupplierTest {

    private Supplier<Integer> supplier = new Supplier<Integer>() {
        public Integer get() {
            return 111;
        }
    };



    @Test
    public void getTest() {
        System.out.println(supplier.get());
    }


    @Test
    public void memoizeTest(){
        System.out.println(Suppliers.memoize(supplier).get());
    }

    @Test
    public void memoizeTest01(){
        System.out.println(Suppliers.memoizeWithExpiration(supplier,100, TimeUnit.MILLISECONDS));
    }

}
