package net.tlipinski.commands;

import net.tlipinski.MidiCommand;
import net.tlipinski.Tracks;

public class TrackBankDownCommand implements MidiCommand {

    public TrackBankDownCommand(Tracks tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (data1 == 36);
    }

    @Override
    public void handle(int data1, int data2) {
        tracks.getTrackBank().scrollChannelsPageDown();
    }

    private final Tracks tracks;

}
