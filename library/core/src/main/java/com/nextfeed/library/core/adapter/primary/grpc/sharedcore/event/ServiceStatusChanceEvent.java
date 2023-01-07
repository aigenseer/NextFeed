package com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event;

import org.springframework.context.ApplicationEvent;

public class ServiceStatusChanceEvent extends ApplicationEvent {

    public ServiceStatusChanceEvent(Object source) {
        super(source);
    }

}
