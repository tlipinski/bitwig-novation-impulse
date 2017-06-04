package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.MidiCommand;
import net.tlipinski.bitwig.controller.Controller;

public class TrackBankUpCommand implements MidiCommand {

    public TrackBankUpCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (data1 == 35);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getTracks().getTrackBank().scrollChannelsPageUp();
    }

    private final Controller controller;

}
