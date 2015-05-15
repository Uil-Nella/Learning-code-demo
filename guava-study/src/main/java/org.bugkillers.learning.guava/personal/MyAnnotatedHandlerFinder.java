package org.bugkillers.learning.guava.personal;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class MyAnnotatedHandlerFinder implements MyHandlerFindingStrategy {
    private static final LoadingCache<Class<?>, ImmutableList<Method>> handlerMethodsCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader() {
        @Override
        public Object load(Object o) throws Exception {
            return null;
        }

        public ImmutableList<Method> load(Class<?> concreteClass) throws Exception {
            return MyAnnotatedHandlerFinder.getAnnotatedMethodsInternal(concreteClass);
        }
    });

    MyAnnotatedHandlerFinder() {
    }

    public Multimap<Class<?>, MyEventHandler> findAllHandlers(Object listener) {
        HashMultimap methodsInListener = HashMultimap.create();
        Class clazz = listener.getClass();
        Iterator i$ = getAnnotatedMethods(clazz).iterator();

        while(i$.hasNext()) {
            Method method = (Method)i$.next();
            Class[] parameterTypes = method.getParameterTypes();
            Class eventType = parameterTypes[0];
            MyEventHandler handler = makeHandler(listener, method);
            methodsInListener.put(eventType, handler);
        }

        return methodsInListener;
    }

    private static ImmutableList<Method> getAnnotatedMethods(Class<?> clazz) {
        try {
            return (ImmutableList)handlerMethodsCache.getUnchecked(clazz);
        } catch (UncheckedExecutionException var2) {
            throw Throwables.propagate(var2.getCause());
        }
    }

    private static ImmutableList<Method> getAnnotatedMethodsInternal(Class<?> clazz) {
        Set supers = TypeToken.of(clazz).getTypes().rawTypes();
        HashMap identifiers = Maps.newHashMap();
        Iterator i$ = supers.iterator();

        while(i$.hasNext()) {
            Class superClazz = (Class)i$.next();
            Method[] arr$ = superClazz.getMethods();
            int len$ = arr$.length;

            for(int i$1 = 0; i$1 < len$; ++i$1) {
                Method superClazzMethod = arr$[i$1];
                if(superClazzMethod.isAnnotationPresent(Subscribe.class)) {
                    Class[] parameterTypes = superClazzMethod.getParameterTypes();
                    if(parameterTypes.length != 1) {
                        throw new IllegalArgumentException("Method " + superClazzMethod + " has @Subscribe annotation, but requires " + parameterTypes.length + " arguments.  Event handler methods must require a single argument.");
                    }

                    MyAnnotatedHandlerFinder.MethodIdentifier ident = new MyAnnotatedHandlerFinder.MethodIdentifier(superClazzMethod);
                    if(!identifiers.containsKey(ident)) {
                        identifiers.put(ident, superClazzMethod);
                    }
                }
            }
        }

        return ImmutableList.copyOf(identifiers.values());
    }

    private static MyEventHandler makeHandler(Object listener, Method method) {
        Object wrapper;
        if(methodIsDeclaredThreadSafe(method)) {
            wrapper = new MyEventHandler(listener, method);
        } else {
            wrapper = new MySynchronizedEventHandler(listener, method);
        }

        return (MyEventHandler)wrapper;
    }

    private static boolean methodIsDeclaredThreadSafe(Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }

    private static final class MethodIdentifier {
        private final String name;
        private final List<Class<?>> parameterTypes;

        MethodIdentifier(Method method) {
            this.name = method.getName();
            this.parameterTypes = Arrays.asList(method.getParameterTypes());
        }

        public int hashCode() {
            return Objects.hashCode(new Object[]{this.name, this.parameterTypes});
        }

        public boolean equals(/**@Nullable*/ Object o) {
            if(!(o instanceof MyAnnotatedHandlerFinder.MethodIdentifier)) {
                return false;
            } else {
                MyAnnotatedHandlerFinder.MethodIdentifier ident = (MyAnnotatedHandlerFinder.MethodIdentifier)o;
                return this.name.equals(ident.name) && this.parameterTypes.equals(ident.parameterTypes);
            }
        }
    }
}