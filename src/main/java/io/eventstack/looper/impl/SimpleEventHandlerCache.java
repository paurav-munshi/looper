package io.eventstack.looper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.eventstack.looper.EventHandler;
import io.eventstack.looper.EventHandlerCache;

public class SimpleEventHandlerCache implements EventHandlerCache
{
    private Map<String, List<EventHandler>> eventsCache = new ConcurrentHashMap<String, EventHandler>();

    @Override
    public void registerEvent(String eventName, EventHandler handler) throws Exception {
        // TODO Auto-generated method stub
        if(!eventsCache.containsKey(eventName)) eventsCache.put(eventName, new ArrayList<EventHandler>());

        eventsCache.get(eventName).add(handler);

    }

    @Override
    public List<EventHandler> getHandlersForEvent(String eventName) throws Exception {
        // TODO Auto-generated method stub
        return eventsCache.get(eventName);
    }
}