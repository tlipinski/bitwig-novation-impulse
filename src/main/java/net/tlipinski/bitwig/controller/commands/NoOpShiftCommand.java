package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.MidiCommand;

public class NoOpShiftCommand implements MidiCommand {

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (statusByte == 0xB0) && (data1 == 39);
    }

    @Override
    public void handle(int data1, int data2) {

    }
}
