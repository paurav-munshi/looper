package io.eventstack.looper.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.eventstack.looper.Event;
import io.eventstack.looper.EventHandler;
import io.eventstack.looper.EventLoop;
import io.eventstack.looper.EventLoopException;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicBoolean;



public class SimpleEventLoopTest {

    SimpleEventLoop sel;
    SimpleEventHandlerCache sec;
    EventHandler<EventLoop> loopEnder = new EventHandler<EventLoop>() {

        @Override
        public void handleEvent(Event<EventLoop> event) {
            // TODO Auto-generated method stub
            EventLoop el = event.getData();
            try {
                el.stop();
            } catch (EventLoopException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    };

    @BeforeAll
    public void setup() throws Exception {
        sel = new SimpleEventLoop();
        sec = new SimpleEventHandlerCache();
        sel.registerEventHandlerCache(sec);
        sec.registerEvent("End Loop", loopEnder);
    }

    @Test
    @DisplayName("Test that SimpleEventLoop is accepting events addted to it ")
    public void testSimpleEventProcessor() throws Exception{
        AtomicBoolean shouldWait = new AtomicBoolean(true);
        sec.registerEvent("Greet Event", new EventHandler<String>(){
        
            @Override
            public void handleEvent(Event<String> event) {
                // TODO Auto-generated method stub
                System.out.println("Hello " + event.getData());
                shouldWait.set(false);
            }
        });

        Event<String> event = new Event<String>();
        event.setName("Greet Event");
        event.setData("Paurav");
        sel.addEvent(event);

        Event<EventLoop> endEvent = new Event<EventLoop>();
        endEvent.setName("End Loop");
        endEvent.setData(sel);
        sel.addEvent(endEvent);
        
        Thread evl = new Thread(sel);
        evl.start();

        evl.join();
        assertEquals("testSimpleEventProcessor passed", 1, 1);
    }

    @Test
    public void testEventHandlerNotPresent() {
        // TODO: Add a test where you fire an event whose Evenh Handler does not exist.
    }

    @Test()
    public void testEventsInQueueIgnoredAfterStop(){

    }
}