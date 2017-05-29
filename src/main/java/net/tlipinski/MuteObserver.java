package net.tlipinski;

import com.bitwig.extension.controller.api.Track;

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
