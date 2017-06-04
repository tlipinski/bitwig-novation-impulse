package net.tlipinski.bitwig.controller.observers.callbacks;

import com.bitwig.extension.callback.BooleanValueChangedCallback;
import net.tlipinski.bitwig.controller.ButtonsMode;
import net.tlipinski.bitwig.controller.MidiSend;
import net.tlipinski.bitwig.controller.Controller;

public class SoloCallback implements BooleanValueChangedCallback {
    private final int idx;
    private final Controller controller;
    private final MidiSend midiSend;

    public SoloCallback(int idx, Controller controller, MidiSend midiSend) {
        this.idx = idx;
        this.controller = controller;
        this.midiSend = midiSend;
    }

    @Override
    public void valueChanged(boolean solo) {
        if (controller.getButtonsMode() == ButtonsMode.SOLO) {
            if (controller.getTracks().isNotEmpty(idx)) {
                midiSend.light(idx, solo);
            } else {
                midiSend.light(idx, false);
            }
        }
    }
}
