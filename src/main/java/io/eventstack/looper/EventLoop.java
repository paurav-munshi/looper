package io.eventstack.looper;

/*
    This class represents a holder of fired events which it will eventually process in a particular sequence.
    How to process the events and in what sequence is up to the implementation and several implementation can exist.
    Example could be SiimpleEventLoop (event loop execute handler in same thread),SingleThreadedEventLoop, MultiThreadedEventloop

    To add an event to the event loop addEvent method is used. To add an event the event source will use an EventEmitter
    and fire the event. EventEmitter can add the event to the event loop using this method.


*/

public interface EventLoop {
    public <T> void addEvent(Event<T> event) throws EventLoopException;
    public void addEventPostExecHandler(EventHandler event) throws EventLoopException;
    public void registerEventHandlerCache(EventHandlerCache cache) throws EventLoopException;
    public long size();

}