package net.tlipinski;

import com.bitwig.extension.controller.api.ControllerHost;

public class MidiSend {

    public MidiSend(ControllerHost host) {
        this.host = host;
    }

    public void light(int index, boolean on) {
        if (on) {
            lightOn(index);
        } else {
            lightOff(index);
        }
    }

    public void resetRotaryMode() {
        host.getMidiOutPort(0).sendMidi(0xB1, 10, 1);
    }

    private void lightOn(int index) {
        host.getMidiOutPort(0).sendMidi(0xB0, 9 + index, 1);
    }

    private void lightOff(int index) {
        host.getMidiOutPort(0).sendMidi(0xB0, 9 + index, 0);
    }

    private final ControllerHost host;
}
