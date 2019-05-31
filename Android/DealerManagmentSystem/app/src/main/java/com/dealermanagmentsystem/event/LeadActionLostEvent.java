package com.dealermanagmentsystem.event;


public class LeadActionLostEvent extends BaseEvent {

    public LeadActionLostEvent(String isAction) {
        super(isAction);
    }

    @Override
    public String getCastedObject() {
        return (String) getObject();
    }

}