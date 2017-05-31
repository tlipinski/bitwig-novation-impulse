package net.tlipinski.observers.callbacks;

import com.bitwig.extension.callback.BooleanValueChangedCallback;
import net.tlipinski.ButtonsMode;
import net.tlipinski.MidiSend;
import net.tlipinski.Tracks;

public class SoloCallback implements BooleanValueChangedCallback {
    private final int idx;
    private final Tracks tracks;
    private final MidiSend midiSend;

    public SoloCallback(int idx, Tracks tracks, MidiSend midiSend) {
        this.idx = idx;
        this.tracks = tracks;
        this.midiSend = midiSend;
    }

    @Override
    public void valueChanged(boolean solo) {
        if (tracks.getButtonsMode() == ButtonsMode.SOLO) {
            if (tracks.isNotEmpty(idx)) {
                midiSend.light(idx, solo);
            } else {
                midiSend.light(idx, false);
            }
        }
    }
}
