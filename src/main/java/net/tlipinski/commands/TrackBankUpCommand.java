package net.tlipinski.commands;

import net.tlipinski.MidiCommand;
import net.tlipinski.SysexSend;
import net.tlipinski.Controller;

public class TrackBankUpCommand implements MidiCommand {

    public TrackBankUpCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;

        controller.getTracks().getTrackBank().scrollPosition().addValueObserver((int pos) -> {
            // track.scrollPosition().get() was showing stale values
            sysexSend.displayText("[" + (pos + 1) + "-" + (pos + 8) + "]");
        });
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
