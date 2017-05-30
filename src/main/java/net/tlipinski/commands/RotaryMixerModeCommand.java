package net.tlipinski.commands;

import net.tlipinski.*;

public class RotaryMixerModeCommand implements MidiCommand {

    public RotaryMixerModeCommand(Tracks tracks, MidiSend midiSend, SysexSend sysexSend) {
        this.tracks = tracks;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;

        midiSend.resetRotaryMode();
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB1 && (data1 == 9);
    }

    @Override
    public void handle(int data1, int data2) {
        tracks.changeRotaryMode(RotaryMode.MIXER);
        sysexSend.displayText("Mixer");
    }

    private Tracks tracks;
    private final MidiSend midiSend;
    private SysexSend sysexSend;

}
