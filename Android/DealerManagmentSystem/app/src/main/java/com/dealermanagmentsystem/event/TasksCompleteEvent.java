package com.dealermanagmentsystem.event;


public class TasksCompleteEvent extends BaseEvent {

    public TasksCompleteEvent(String id) {
        super(id);
    }

    @Override
    public String getCastedObject() {
        return (String) getObject();
    }

}