package net.tlipinski.bitwig.controller.observers.callbacks;

import com.bitwig.extension.callback.ObjectValueChangedCallback;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.ButtonsMode;
import net.tlipinski.bitwig.controller.MidiSend;
import net.tlipinski.bitwig.controller.Controller;

import java.util.List;

public class RefreshAllLightsCallback implements ObjectValueChangedCallback<ButtonsMode> {
    private final Controller controller;
    private final MidiSend midiSend;

    public RefreshAllLightsCallback(Controller controller, MidiSend midiSend) {
        this.controller = controller;
        this.midiSend = midiSend;
    }

    @Override
    public void valueChanged(ButtonsMode newValue) {
        List<Track> all = controller.getTracks().getAll();
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
        if (controller.getButtonsMode() == ButtonsMode.MUTE) {
            if (controller.getTracks().isNotEmpty(idx)) {
                midiSend.light(idx, !mute);
            } else {
                midiSend.light(idx, false);
            }
        }
    }

    private void handleSolo(int idx, boolean solo) {
        if (controller.getButtonsMode() == ButtonsMode.SOLO) {
            if (controller.getTracks().isNotEmpty(idx)) {
                midiSend.light(idx, solo);
            } else {
                midiSend.light(idx, false);
            }
        }
    }
}
