package com.dealermanagmentsystem.event;


public class LeadActionWonEvent extends BaseEvent {

    public LeadActionWonEvent(String isAction) {
        super(isAction);
    }

    @Override
    public String getCastedObject() {
        return (String) getObject();
    }

}