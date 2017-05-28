package net.tlipinski.commands;

import net.tlipinski.MidiCommand;
import net.tlipinski.Tracks;

public class TrackBankUpCommand implements MidiCommand {

    public TrackBankUpCommand(Tracks tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (data1 == 35);
    }

    @Override
    public void handle(int data1, int data2) {
        tracks.getTrackBank().scrollChannelsPageUp();
    }

    private final Tracks tracks;

}
