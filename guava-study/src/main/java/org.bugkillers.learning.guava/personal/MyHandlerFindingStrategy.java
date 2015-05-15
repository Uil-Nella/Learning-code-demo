package org.bugkillers.learning.guava.personal;

import com.google.common.collect.Multimap;

interface MyHandlerFindingStrategy {
    Multimap<Class<?>, MyEventHandler> findAllHandlers(Object var1);
}
