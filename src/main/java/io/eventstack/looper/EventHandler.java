package io.eventstack.looper;

@FunctionalInterface
public interface EventHandler {
    public void handleEvent(Event event);
}