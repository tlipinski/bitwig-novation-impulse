package net.tlipinski.bitwig.controller;

import com.bitwig.extension.callback.ShortMidiDataReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;

import java.util.List;

public class MidiCallback implements ShortMidiDataReceivedCallback {
    public MidiCallback(ControllerHost host, Preferences prefs, List<MidiCommand> midiCommands) {
        this.host = host;
        this.prefs = prefs;
        this.midiCommands = midiCommands;
    }

    @Override
    public void midiReceived(int statusByte, int data1, int data2) {
        for (MidiCommand cmd : midiCommands) {
            if (cmd.isTriggeredWhen(statusByte, data1, data2)) {
                if (prefs.debug()) {
                    host.println("[" + String.format("%2X", statusByte) + ":" + data1 + ":" + data2 + "] -> " + cmd.getClass().getSimpleName());
                }
                cmd.handle(data1, data2);
                return ;
            }
        }

        if (prefs.debug()) {
            host.errorln("[" + String.format("%2X", statusByte) + ":" + data1 + ":" + data2 + "] -> unhandled");
        }
    }

    private ControllerHost host;
    private final Preferences prefs;
    private final List<MidiCommand> midiCommands;
}
