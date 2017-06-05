package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class ShiftPressedCommand implements MidiCommand {

    public ShiftPressedCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Stream<Boolean> conditions(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB1,
                data1 == 13,
                data2 == 1
        );
    }

    @Override
    public void handle(int data1, int data2) {
        controller.shiftPressed();
    }

    private final Controller controller;
}
