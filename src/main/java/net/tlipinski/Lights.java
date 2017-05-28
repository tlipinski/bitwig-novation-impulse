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
        if (tracks.getButtonsMode() == ButtonsMode.MUTE) {
            if (tracks.isNotEmpty(idx)) {
                midiSend.light(idx, !mute);
            } else {
                midiSend.light(idx, false);
            }
        }
    }

    private void handleSolo(int idx, boolean solo) {
        if (tracks.getButtonsMode() == ButtonsMode.SOLO) {
            if (tracks.isNotEmpty(idx)) {
                midiSend.light(idx, solo);
            } else {
                midiSend.light(idx, false);
            }
        }
    }

    private void initButtonModeObserver() {
        tracks.addButtonsModeObserver(this::refreshAllLights);

        tracks.getTrackBank().channelCount().addValueObserver((int count) -> {
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
