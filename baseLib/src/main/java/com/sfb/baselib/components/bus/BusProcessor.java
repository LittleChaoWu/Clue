package com.sfb.baselib.components.bus;

import io.reactivex.processors.FlowableProcessor;

public class BusProcessor<T> {

    private Object event;
    private FlowableProcessor<T> flowableProcessor;

    public BusProcessor(FlowableProcessor<T> flowableProcessor) {
        this.flowableProcessor = flowableProcessor;
    }

    public BusProcessor(Object event, FlowableProcessor<T> flowableProcessor) {
        this.event = event;
        this.flowableProcessor = flowableProcessor;
    }

    public void setEvent(Object event) {
        this.event = event;
    }

    public void setFlowableProcessor(FlowableProcessor<T> flowableProcessor) {
        this.flowableProcessor = flowableProcessor;
    }

    public Object getEvent() {
        return event;
    }

    public FlowableProcessor<T> getFlowableProcessor() {
        return flowableProcessor;
    }
}
