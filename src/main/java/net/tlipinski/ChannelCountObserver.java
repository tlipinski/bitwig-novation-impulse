package net.tlipinski;

import com.bitwig.extension.controller.api.Track;

public class ChannelCountObserver {

    public ChannelCountObserver(Tracks tracks, MidiSend midiSend) {
        for (Track t : tracks.getAll()) {
            t.getMute().markInterested();
            t.getSolo().markInterested();
        }

        tracks.getTrackBank().channelCount().addValueObserver((int count) -> {
            new RefreshAllLightsCallback(tracks, midiSend).valueChanged(tracks.getButtonsMode());
        });
    }
}
