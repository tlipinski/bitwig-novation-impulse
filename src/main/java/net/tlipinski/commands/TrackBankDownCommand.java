package net.tlipinski.commands;

import net.tlipinski.MidiCommand;
import net.tlipinski.SysexSend;
import net.tlipinski.Tracks;

public class TrackBankDownCommand implements MidiCommand {

    public TrackBankDownCommand(Tracks tracks, SysexSend sysexSend) {
        this.tracks = tracks;

        tracks.getTrackBank().scrollPosition().addValueObserver((int pos) -> {
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
        tracks.getTrackBank().scrollChannelsPageDown();
    }

    private final Tracks tracks;

}
