package net.tlipinski.commands;

import net.tlipinski.*;

public class ShiftPressedCommand implements MidiCommand {

    public ShiftPressedCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (statusByte == 0xB1) && (data1 == 13) && (data2 == 1);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.shiftPressed();
    }

    private final Controller controller;
}