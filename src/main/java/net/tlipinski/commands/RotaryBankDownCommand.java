package net.tlipinski.commands;

import net.tlipinski.MidiCommand;
import net.tlipinski.Tracks;

public class RotaryBankDownCommand implements MidiCommand {

    public RotaryBankDownCommand(Tracks tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB1 && (data1 == 12);
    }

    @Override
    public void handle(int data1, int data2) {
        tracks.getCursorRemoteControlsPage().selectNextPage(false);
    }

    private final Tracks tracks;

}
