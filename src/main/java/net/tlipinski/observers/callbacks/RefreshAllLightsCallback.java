package net.tlipinski.observers.callbacks;

import com.bitwig.extension.callback.ObjectValueChangedCallback;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.ButtonsMode;
import net.tlipinski.MidiSend;
import net.tlipinski.Tracks;

import java.util.List;

public class RefreshAllLightsCallback implements ObjectValueChangedCallback<ButtonsMode> {
    private final Tracks tracks;
    private final MidiSend midiSend;

    public RefreshAllLightsCallback(Tracks tracks, MidiSend midiSend) {
        this.tracks = tracks;
        this.midiSend = midiSend;
    }

    @Override
    public void valueChanged(ButtonsMode newValue) {
        List<Track> all = tracks.getAll();
        for (int i = 0; i < all.size(); i++) {
            Track track = all.get(i);

            if (newValue == ButtonsMode.MUTE) {
                handleMute(i, track.getMute().get());
            }
            if (newValue == ButtonsMode.SOLO) {
                handleSolo(i, track.getSolo().get());
            }
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
}
