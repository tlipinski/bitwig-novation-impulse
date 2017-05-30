package net.tlipinski.commands;

import net.tlipinski.*;

public class RotaryPluginModeCommand implements MidiCommand {

    public RotaryPluginModeCommand(Tracks tracks, MidiSend midiSend, SysexSend sysexSend) {
        this.tracks = tracks;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB1 && (data1 == 10);
    }

    @Override
    public void handle(int data1, int data2) {
        tracks.changeRotaryMode(RotaryMode.PLUGIN);
        sysexSend.displayText("Plugin");
    }

    private Tracks tracks;
    private final MidiSend midiSend;
    private SysexSend sysexSend;

}
