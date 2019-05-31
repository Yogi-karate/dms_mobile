package com.dealermanagmentsystem.event;


public class LeadActionMoveEvent extends BaseEvent {

    public LeadActionMoveEvent(String isAction) {
        super(isAction);
    }

    @Override
    public String getCastedObject() {
        return (String) getObject();
    }

}