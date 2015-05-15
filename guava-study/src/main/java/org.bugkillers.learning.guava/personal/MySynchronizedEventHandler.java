package org.bugkillers.learning.guava.personal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class MySynchronizedEventHandler extends MyEventHandler {
    public MySynchronizedEventHandler(Object target, Method method) {
        super(target, method);
    }

    public void handleEvent(Object event) throws InvocationTargetException {
        synchronized(this) {
            super.handleEvent(event);
        }
    }
}
