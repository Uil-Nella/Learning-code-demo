package org.bugkillers.learning.guava.personal;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;

@Beta
public class MyDeadEvent {
    private final Object source;
    private final Object event;

    public MyDeadEvent(Object source, Object event) {
        this.source = Preconditions.checkNotNull(source);
        this.event = Preconditions.checkNotNull(event);
    }

    public Object getSource() {
        return this.source;
    }

    public Object getEvent() {
        return this.event;
    }
}
