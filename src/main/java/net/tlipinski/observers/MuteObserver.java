package net.tlipinski.observers;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.MidiSend;
import net.tlipinski.Tracks;
import net.tlipinski.observers.callbacks.MuteCallback;

import java.util.List;

public class MuteObserver {

    public MuteObserver(Tracks tracks, MidiSend midiSend) {
        List<Track> all = tracks.getAll();
        for (int i = 0; i < all.size(); i++) {
            Track t = all.get(i);
            t.getMute().addValueObserver(new MuteCallback(i, tracks, midiSend));
        }
    }

}
