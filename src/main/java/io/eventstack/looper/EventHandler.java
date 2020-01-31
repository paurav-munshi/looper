package io.eventstack.looper;

@FunctionalInterface
public interface EventHandler<T extends Object> {
    public void handleEvent(Event<T> event);
}