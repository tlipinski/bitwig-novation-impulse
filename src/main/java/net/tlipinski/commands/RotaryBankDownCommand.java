package net.tlipinski.commands;

import net.tlipinski.MidiCommand;
import net.tlipinski.Controller;

public class RotaryBankDownCommand implements MidiCommand {

    public RotaryBankDownCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB1 && (data1 == 12);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getTracks().getCursorRemoteControlsPage().selectNextPage(false);
    }

    private final Controller controller;

}
