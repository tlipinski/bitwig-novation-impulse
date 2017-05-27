package net.tlipinski;

import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.Track;

import java.util.List;

public class Lights {
    public Lights(ControllerHost host, Tracks tracks, MidiSend midiSend) {
        this.host = host;
        this.tracks = tracks;
        this.midiSend = midiSend;

        init();
    }

    public void init() {
        initButtonModeObserver();
        initValueObserver();
    }

    public void initValueObserver() {
        List<Track> all = tracks.getAll();
        for (int i = 0; i < all.size(); i++) {
            final int idx = i;
            Track t = all.get(i);

            t.getMute().addValueObserver((boolean mute) -> {
                handleMute(idx, mute);
            });

            t.getSolo().addValueObserver((boolean solo) -> {
                handleSolo(idx, solo);
            });
        }

    }

    private void handleMute(int idx, boolean mute) {
        if (tracks.isNotEmpty(idx)) {
            if (mute) {
                midiSend.lightOff(idx);
            } else {
                midiSend.lightOn(idx);
            }
        } else {
            midiSend.lightOff(idx);
        }
    }

    private void handleSolo(int idx, boolean solo) {
        if (tracks.isNotEmpty(idx)) {
            if (solo) {
                midiSend.lightOn(idx);
            } else {
                midiSend.lightOff(idx);
            }
        } else {
            midiSend.lightOff(idx);
        }
    }

    private void initButtonModeObserver() {
        tracks.addButtonsModeObserver(this::refreshAllLights);
        tracks.setChannelCountObserver(() -> {
            refreshAllLights(tracks.getButtonsMode());
        });
    }

    private void refreshAllLights(ButtonsMode mode) {
        List<Track> all = tracks.getAll();
        for (int i = 0; i < all.size(); i++) {
            Track track = all.get(i);

            if (mode == ButtonsMode.MUTE) {
                handleMute(i, track.getMute().get());
            }
            if (mode == ButtonsMode.SOLO) {
                handleSolo(i, track.getSolo().get());
            }
        }
    }

    private final ControllerHost host;
    private final Tracks tracks;
    private final MidiSend midiSend;
}
