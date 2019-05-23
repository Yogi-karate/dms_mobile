package com.dealermanagmentsystem.event;

/**
 * Created by nikhil.v
 */

public abstract class BaseEvent {

    private final Object object;

    protected BaseEvent(Object object) {
        this.object = object;
    }

    protected Object getObject() {
        return object;
    }

    public abstract Object getCastedObject();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEvent baseEvent = (BaseEvent) o;

        return object.equals(baseEvent.object);

    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }
}
