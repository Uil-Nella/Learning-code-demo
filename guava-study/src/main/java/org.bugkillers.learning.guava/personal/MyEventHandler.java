package org.bugkillers.learning.guava.personal;

import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MyEventHandler {
    private final Object target;
    private final Method method;

    MyEventHandler(Object target, Method method) {
        Preconditions.checkNotNull(target, "EventHandler target cannot be null.");
        Preconditions.checkNotNull(method, "EventHandler method cannot be null.");
        this.target = target;
        this.method = method;
        method.setAccessible(true);
    }

    public void handleEvent(Object event) throws InvocationTargetException {
        Preconditions.checkNotNull(event);

        try {
            this.method.invoke(this.target, new Object[]{event});
        } catch (IllegalArgumentException var3) {
            throw new Error("Method rejected target/argument: " + event, var3);
        } catch (IllegalAccessException var4) {
            throw new Error("Method became inaccessible: " + event, var4);
        } catch (InvocationTargetException var5) {
            if(var5.getCause() instanceof Error) {
                throw (Error)var5.getCause();
            } else {
                throw var5;
            }
        }
    }

    public String toString() {
        return "[wrapper " + this.method + "]";
    }

    public int hashCode() {
        boolean PRIME = true;
        return (31 + this.method.hashCode()) * 31 + System.identityHashCode(this.target);
    }

    public boolean equals(/**@Nullable*/ Object obj) {
        if(!(obj instanceof MyEventHandler)) {
            return false;
        } else {
            MyEventHandler that = (MyEventHandler)obj;
            return this.target == that.target && this.method.equals(that.method);
        }
    }
}
