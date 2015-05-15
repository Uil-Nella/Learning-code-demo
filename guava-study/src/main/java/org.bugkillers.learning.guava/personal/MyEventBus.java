package org.bugkillers.learning.guava.personal;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.eventbus.DeadEvent;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
public class MyEventBus {
    private static final LoadingCache<Class<?>, Set<Class<?>>> flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader() {
        @Override
        public Object load(Object o) throws Exception {
            return null;
        }

        public Set<Class<?>> load(Class<?> concreteClass) {
            return TypeToken.of(concreteClass).getTypes().rawTypes();
        }
    });
    private final SetMultimap<Class<?>, MyEventHandler> handlersByType;
    private final ReadWriteLock handlersByTypeLock;
    private final Logger logger;
    private final MyHandlerFindingStrategy finder;
    private final ThreadLocal<Queue<MyEventBus.EventWithHandler>> eventsToDispatch;
    private final ThreadLocal<Boolean> isDispatching;

    public MyEventBus() {
        this("default");
    }

    public MyEventBus(String identifier) {
        this.handlersByType = HashMultimap.create();
        this.handlersByTypeLock = new ReentrantReadWriteLock();
        this.finder = new MyAnnotatedHandlerFinder();
        this.eventsToDispatch = new ThreadLocal() {
            protected Queue<MyEventBus.EventWithHandler> initialValue() {
                return new LinkedList();
            }
        };
        this.isDispatching = new ThreadLocal() {
            protected Boolean initialValue() {
                return Boolean.valueOf(false);
            }
        };
        this.logger = Logger.getLogger(MyEventBus.class.getName() + "." + (String)Preconditions.checkNotNull(identifier));
    }

    public void register(Object object) {
        Multimap methodsInListener = this.finder.findAllHandlers(object);
        this.handlersByTypeLock.writeLock().lock();

        try {
            this.handlersByType.putAll(methodsInListener);
        } finally {
            this.handlersByTypeLock.writeLock().unlock();
        }

    }

    public void unregister(Object object) {
        Multimap methodsInListener = this.finder.findAllHandlers(object);
        Iterator i$ = methodsInListener.asMap().entrySet().iterator();

        while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            Class eventType = (Class)entry.getKey();
            Collection eventMethodsInListener = (Collection)entry.getValue();
            this.handlersByTypeLock.writeLock().lock();

            try {
                Set currentHandlers = this.handlersByType.get(eventType);
                if(!currentHandlers.containsAll(eventMethodsInListener)) {
                    throw new IllegalArgumentException("missing event handler for an annotated method. Is " + object + " registered?");
                }

                currentHandlers.removeAll(eventMethodsInListener);
            } finally {
                this.handlersByTypeLock.writeLock().unlock();
            }
        }

    }

    public void post(Object event) {
        Set dispatchTypes = this.flattenHierarchy(event.getClass());
        boolean dispatched = false;
        Iterator i$ = dispatchTypes.iterator();

        while(i$.hasNext()) {
            Class eventType = (Class)i$.next();
            this.handlersByTypeLock.readLock().lock();

            try {
                Set wrappers = this.handlersByType.get(eventType);
                if(!wrappers.isEmpty()) {
                    dispatched = true;
                    Iterator i$1 = wrappers.iterator();

                    while(i$1.hasNext()) {
                        MyEventHandler wrapper = (MyEventHandler)i$1.next();
                        this.enqueueEvent(event, wrapper);
                    }
                }
            } finally {
                this.handlersByTypeLock.readLock().unlock();
            }
        }

        if(!dispatched && !(event instanceof DeadEvent)) {
            this.post(new DeadEvent(this, event));
        }

        this.dispatchQueuedEvents();
    }

    void enqueueEvent(Object event, MyEventHandler handler) {
        ((Queue)this.eventsToDispatch.get()).offer(new MyEventBus.EventWithHandler(event, handler));
    }

    void dispatchQueuedEvents() {
        if(!((Boolean)this.isDispatching.get()).booleanValue()) {
            this.isDispatching.set(Boolean.valueOf(true));

            try {
                Queue events = (Queue)this.eventsToDispatch.get();

                MyEventBus.EventWithHandler eventWithHandler;
                while((eventWithHandler = (MyEventBus.EventWithHandler)events.poll()) != null) {
                    this.dispatch(eventWithHandler.event, eventWithHandler.handler);
                }
            } finally {
                this.isDispatching.remove();
                this.eventsToDispatch.remove();
            }

        }
    }

    void dispatch(Object event, MyEventHandler wrapper) {
        try {
            wrapper.handleEvent(event);
        } catch (InvocationTargetException var4) {
            this.logger.log(Level.SEVERE, "Could not dispatch event: " + event + " to handler " + wrapper, var4);
        }

    }

    @VisibleForTesting
    Set<Class<?>> flattenHierarchy(Class<?> concreteClass) {
        try {
            return (Set)flattenHierarchyCache.getUnchecked(concreteClass);
        } catch (UncheckedExecutionException var3) {
            throw Throwables.propagate(var3.getCause());
        }
    }

    static class EventWithHandler {
        final Object event;
        final MyEventHandler handler;

        public EventWithHandler(Object event, MyEventHandler handler) {
            this.event = Preconditions.checkNotNull(event);
            this.handler = (MyEventHandler)Preconditions.checkNotNull(handler);
        }
    }
}