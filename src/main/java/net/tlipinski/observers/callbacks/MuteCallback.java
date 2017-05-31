package net.tlipinski.observers.callbacks;

import com.bitwig.extension.callback.BooleanValueChangedCallback;
import net.tlipinski.ButtonsMode;
import net.tlipinski.MidiSend;
import net.tlipinski.Tracks;

public class MuteCallback implements BooleanValueChangedCallback {
    private final int idx;
    private final Tracks tracks;
    private final MidiSend midiSend;

    public MuteCallback(int idx, Tracks tracks, MidiSend midiSend) {
        this.idx = idx;
        this.tracks = tracks;
        this.midiSend = midiSend;
    }

    @Override
    public void valueChanged(boolean mute) {
        if (tracks.getButtonsMode() == ButtonsMode.MUTE) {
            if (tracks.isNotEmpty(idx)) {
                midiSend.light(idx, !mute);
            } else {
                midiSend.light(idx, false);
            }
        }
    }
}
