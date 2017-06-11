package net.tlipinski.bitwig.controller;

import java.util.stream.Stream;

public interface MidiCommand {
    default boolean isTriggeredWhen(int statusByte, int data1, int data2) {
        return triggersWhen(statusByte, data1, data2).allMatch(b -> b);
    }

    Stream<Boolean> triggersWhen(int statusByte, int data1, int data2);

    void handle(int data1, int data2);
}
