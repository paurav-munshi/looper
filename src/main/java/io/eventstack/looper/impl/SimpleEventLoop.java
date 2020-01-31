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

    private volatile boolean shouldStop = false;

    @Override
    public <T> void addEvent(Event<T> event) throws EventLoopException {
        // TODO Auto-generated method stub
        throwExcpetionIfStopped("Event Loop is stopped or signaled to be stopped. Cant take new events");
        eventsQueue.offer(event);
        

    }

    @Override
    public void addEventPostExecHandler(EventHandler eventHandler) throws EventLoopException {
        // TODO Auto-generated method stub
        throwExcpetionIfStopped("Event Loop is stopped or signaled to be stopped. Cant add post event handler");
        postEventHandler = eventHandler;
    }

    @Override
    public void registerEventHandlerCache(EventHandlerCache cache) throws EventLoopException {
        // TODO Auto-generated method stub
        throwExcpetionIfStopped("Event Loop is stopped or signaled to be stopped. Cant register event handler cache");
        evhCache = cache;

    }

    @Override
    public long size() {
        // TODO Auto-generated method stub
        return eventsQueue.size();
    }

    private <T> void handleEvent(Event<T> event) {
        try {
            List<EventHandler> handlers = evhCache.getHandlersForEvent(event.getName());
            handlers.forEach(handler -> handler.handleEvent(event));
        }catch(Exception exp) {
            exp.printStackTrace();
        }
    }

    public void run() {
        while(!shouldStop) {
            Event event = eventsQueue.poll();
            if(event!=null) {               
                handleEvent(event);
            }
        }
    }

    @Override
    public void stop() throws EventLoopException {
        // TODO Auto-generated method stub
        shouldStop = true;
        drainAllEvents();
    }

    private void throwExcpetionIfStopped(String message) throws EventLoopException{
        if(shouldStop) throw new EventLoopException(message);
    }

    private void drainAllEvents() {
        while(eventsQueue.poll() !=null) {}
    }
}