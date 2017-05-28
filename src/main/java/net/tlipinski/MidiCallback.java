package net.tlipinski;

import com.bitwig.extension.callback.ShortMidiDataReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;

import java.util.List;

public class MidiCallback implements ShortMidiDataReceivedCallback {
    public MidiCallback(ControllerHost host, List<MidiCommand> midiCommands) {
        this.host = host;
        this.midiCommands = midiCommands;
    }

    @Override
    public void midiReceived(int statusByte, int data1, int data2) {
        for (MidiCommand cmd : midiCommands) {
            if (cmd.triggersFor(statusByte, data1, data2)) {
                cmd.handle(data1, data2);
                return ;
            }
        }

        host.errorln("Unhandled command: " + String.format("%2X", statusByte) + ":" + data1 + ":" + data2);
    }

    private ControllerHost host;
    private final List<MidiCommand> midiCommands;
}
