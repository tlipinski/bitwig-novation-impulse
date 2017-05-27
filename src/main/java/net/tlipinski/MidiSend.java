package net.tlipinski;

import com.bitwig.extension.controller.api.ControllerHost;

public class MidiSend {

    public MidiSend(ControllerHost host) {
        this.host = host;
    }

    public void lightOn(int index) {
        host.getMidiOutPort(0).sendMidi(176, 9 + index, 1);
    }

    public void lightOff(int index) {
        host.getMidiOutPort(0).sendMidi(176, 9 + index, 0);
    }

    private final ControllerHost host;
}
