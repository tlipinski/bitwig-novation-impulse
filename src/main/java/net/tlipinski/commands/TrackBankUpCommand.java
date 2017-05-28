package net.tlipinski.commands;

import net.tlipinski.MidiCommand;
import net.tlipinski.SysexSend;
import net.tlipinski.Tracks;

public class TrackBankUpCommand implements MidiCommand {

    public TrackBankUpCommand(Tracks tracks, SysexSend sysexSend) {
        this.tracks = tracks;

        tracks.getTrackBank().scrollPosition().addValueObserver((int pos) -> {
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
        tracks.getTrackBank().scrollChannelsPageUp();
    }

    private final Tracks tracks;

}
