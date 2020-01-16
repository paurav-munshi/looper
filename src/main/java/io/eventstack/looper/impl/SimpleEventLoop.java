package io.eventstack.looper.impl;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.eventstack.looper.Event;
import io.eventstack.looper.EventException;
import io.eventstack.looper.EventHandler;
import io.eventstack.looper.EventHandlerCache;
import io.eventstack.looper.EventLoop;
import io.eventstack.looper.EventLoopException;

public class SimpleEventLoop implements EventLoop, Runnable {
    private static final Queue<Event> eventsQueue = new ConcurrentLinkedQueue<Event>();
    private EventHandlerCache evhCache = null;
    private EventHandler postEventHandler = null;


    @Override
    public <T> void addEvent(Event<T> event) throws EventLoopException {
        // TODO Auto-generated method stub
        eventsQueue.offer(event);

    }

    @Override
    public void addEventPostExecHandler(EventHandler eventHandler) throws EventLoopException {
        // TODO Auto-generated method stub
        postEventHandler = eventHandler;
    }

    @Override
    public void registerEventHandlerCache(EventHandlerCache cache) throws EventLoopException {
        // TODO Auto-generated method stub
        evhCache = cache;

    }

    @Override
    public long size() {
        // TODO Auto-generated method stub
        return eventsQueue.size();
    }

    private void handleEvent(Event event) {
        try {
            List<EventHandler> handlers = evhCache.getHandlersForEvent(event.getName());
            handlers.forEach(handler -> handler.handleEvent(event));
        }catch(Exception exp) {
            exp.printStackTrace();
        }
    }

    public void run() {
        while(true) {
            Event event = eventsQueue.poll();
            if(event!=null) {
                
                handleEvent(event);

            }
        }
    }
}