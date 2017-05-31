package net.tlipinski.observers;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.MidiSend;
import net.tlipinski.Tracks;
import net.tlipinski.observers.callbacks.RefreshAllLightsCallback;

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
