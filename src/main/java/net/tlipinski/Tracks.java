package net.tlipinski;

import com.bitwig.extension.controller.api.*;

import java.util.ArrayList;
import java.util.List;

public class Tracks {
    public Tracks(ControllerHost host) {
        this.trackBank = host.createTrackBank(8, 2, 8);
        this.masterTrack = host.createMasterTrack(8);

        this.cursorTrack = host.createCursorTrack(2, 0);

        this.cursorDevice = cursorTrack.createCursorDevice();

        PinnableCursorDevice cursorDevice = cursorTrack.createCursorDevice();
        this.cursorRemoteControlsPage = cursorDevice.createCursorRemoteControlsPage(8);

        masterTrack.name().markInterested();
        for (int i = 0; i < trackBank.getSizeOfBank(); i++) {
            Track channel = trackBank.getChannel(i);
            channel.name().markInterested();
            channel.trackType().markInterested();
        }
    }

    public CursorRemoteControlsPage getCursorRemoteControlsPage() {
        return cursorRemoteControlsPage;
    }

    public TrackBank getTrackBank() {
        return trackBank;
    }

    public Track get(int index) {
        if (index == 8) {
            return masterTrack;
        } else {
            Track track = trackBank.getChannel(index);
            if (track.trackType().get().equals("") || track.trackType().get().equals("Master")) {
                return null;
            } else {
                return track;
            }
        }
    }

    public boolean isNotEmpty(int index) {
        return !isEmpty(index);
    }

    public boolean isEmpty(int index) {
        if (index == 8) { // Master
            return false;
        }

        String type = trackBank.getChannel(index).trackType().get();
        if (type.equals("") || type.equals("Master")) {
            return true;
        }

        return false;
    }

    public List<Track> getAll() {
        List result = new ArrayList();
        for (int i = 0; i < trackBank.getSizeOfBank(); i++) {
            result.add(trackBank.getChannel(i));
        }
        result.add(masterTrack);
        return result;
    }

    private final TrackBank trackBank;
    private final MasterTrack masterTrack;
    private final PinnableCursorDevice cursorDevice;

    private final CursorRemoteControlsPage cursorRemoteControlsPage;

    private final CursorTrack cursorTrack;
}
