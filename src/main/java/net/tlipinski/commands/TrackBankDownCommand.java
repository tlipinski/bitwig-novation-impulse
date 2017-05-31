package net.tlipinski.commands;

import net.tlipinski.MidiCommand;
import net.tlipinski.SysexSend;
import net.tlipinski.Controller;

public class TrackBankDownCommand implements MidiCommand {

    public TrackBankDownCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;

        controller.getTracks().getTrackBank().scrollPosition().addValueObserver((int pos) -> {
            // track.scrollPosition().get() was showing stale values
            sysexSend.displayText("[" + (pos + 1) + "-" + (pos + 8) + "]");
        });
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (data1 == 36);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getTracks().getTrackBank().scrollChannelsPageDown();
    }

    private final Controller controller;

}
