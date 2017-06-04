package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.MidiCommand;
import net.tlipinski.bitwig.controller.Controller;

public class EncoderPageDownCommand implements MidiCommand {

    public EncoderPageDownCommand(Controller controller) {
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
