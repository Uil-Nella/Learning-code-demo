package org.bugkillers.learning.guava.personal;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

@Beta
public class MyAsyncEventBus extends MyEventBus {
    private final Executor executor;
    private final ConcurrentLinkedQueue<EventWithHandler> eventsToDispatch = new ConcurrentLinkedQueue();

    public MyAsyncEventBus(String identifier, Executor executor) {
        super(identifier);
        this.executor = (Executor)Preconditions.checkNotNull(executor);
    }

    public MyAsyncEventBus(Executor executor) {
        this.executor = (Executor)Preconditions.checkNotNull(executor);
    }

    void enqueueEvent(Object event, MyEventHandler handler) {
        this.eventsToDispatch.offer(new EventWithHandler(event, handler));
    }

    protected void dispatchQueuedEvents() {
        while(true) {
            EventWithHandler eventWithHandler = (EventWithHandler)this.eventsToDispatch.poll();
            if(eventWithHandler == null) {
                return;
            }

            this.dispatch(eventWithHandler.event, eventWithHandler.handler);
        }
    }

    void dispatch(final Object event, final MyEventHandler handler) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(handler);
        this.executor.execute(new Runnable() {
            public void run() {
                MyAsyncEventBus.super.dispatch(event, handler);
            }
        });
    }
}