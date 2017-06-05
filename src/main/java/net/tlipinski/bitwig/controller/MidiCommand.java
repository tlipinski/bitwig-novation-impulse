package net.tlipinski.bitwig.controller;

import java.util.stream.Stream;

public interface MidiCommand {
    default boolean triggersFor(int statusBute, int data1, int data2) {
        return conditions(statusBute, data1, data2).allMatch(b -> b);
    }

    Stream<Boolean> conditions(int statusByte, int data1, int data2);

    void handle(int data1, int data2);
}
