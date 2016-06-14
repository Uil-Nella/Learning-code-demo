package org.bugkillers.hystrix;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by liuxinyu on 16/6/14.
 */
public class FastFailTest {

    @Test
    public void testSuccess(){
        Assert.assertEquals("success",new CommandThatFailsFast(false).execute() );
    }

    @Test
    public void testFailure() {
        try {
            new CommandThatFailsFast(true).execute();
            fail("we should have thrown an exception");
        } catch (HystrixRuntimeException e) {
            assertEquals("failure from CommandThatFailsFast", e.getCause().getMessage());
            e.printStackTrace();
        }
    }

}
