package net.tlipinski.commands;

import net.tlipinski.Controller;
import net.tlipinski.MidiCommand;
import net.tlipinski.MidiSend;
import net.tlipinski.SysexSend;

public class ShiftReleasedCommand implements MidiCommand {

    public ShiftReleasedCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (statusByte == 0xB1) && (data1 == 13) && (data2 == 0);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.shiftReleased();
    }

    private final Controller controller;
}
