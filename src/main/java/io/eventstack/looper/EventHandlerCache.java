package io.eventstack.looper;

import java.util.List;

public interface EventHandlerCache {
    public void registerEvent(String eventName,EventHandler handler) throws Exception;
    public List<EventHandler> getHandlersForEvent(String eventName) throws Exception;
}