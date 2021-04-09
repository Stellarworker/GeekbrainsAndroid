package com.geekbrains.androidlevel1.event_bus.events;

public class ButtonClickedEvent {
    public int count = 0;

    public ButtonClickedEvent(int count) {
        this.count = count;
    }
}
