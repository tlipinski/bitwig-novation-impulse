package net.tlipinski.bitwig.controller;

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

    public void clipLight(int index, ClipLight light) {
        host.getMidiOutPort(0).sendMidi(0xB0, 60 + index, light.light);
    }

    public void send(int status, int data1, int data2) {
        host.getMidiOutPort(0).sendMidi(status, data1, data2);
    }

    public void resetEncoderMode() {
        host.getMidiOutPort(0).sendMidi(0xB1, 10, 1);
    }

    public void resetButtonsMode() {
        // TODO why it doesn't work?
        host.getMidiOutPort(0).sendMidi(0xB0, 34, 1);
    }

    private void lightOn(int index) {
        host.getMidiOutPort(0).sendMidi(0xB0, 9 + index, 1);
    }

    private void lightOff(int index) {
        host.getMidiOutPort(0).sendMidi(0xB0, 9 + index, 0);
    }

    private final ControllerHost host;
}
