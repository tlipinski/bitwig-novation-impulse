package net.tlipinski.bitwig.controller;

public enum ClipLight {
    EMPTY(0),
    PLAYING(32),
    STOPPED(35),
    STOP_QUEUED(27),
    PLAYBACK_QUEUED(25);


    ClipLight(int light) {
        this.light = light;
    }

    public final int light;
}
