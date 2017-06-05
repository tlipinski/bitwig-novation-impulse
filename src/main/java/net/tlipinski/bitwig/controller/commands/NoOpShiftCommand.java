package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class NoOpShiftCommand implements MidiCommand {

    @Override
    public Stream<Boolean> conditions(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 == 39
        );
    }

    @Override
    public void handle(int data1, int data2) {

    }
}
