package net.tlipinski.commands;

import net.tlipinski.*;

public class ButtonsModeCommand implements MidiCommand {

    public ButtonsModeCommand(Tracks tracks, MidiSend midiSend, SysexSend sysexSend) {
        this.tracks = tracks;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (data1 == 34);
    }

    @Override
    public void handle(int data1, int data2) {
        if (data2 == 1) {
            tracks.changeButtonsMode(ButtonsMode.MUTE);
            new RefreshAllLightsCallback(tracks, midiSend).valueChanged(ButtonsMode.MUTE);
            sysexSend.displayText("Mutes");
        }
        if (data2 == 0){
            tracks.changeButtonsMode(ButtonsMode.SOLO);
            new RefreshAllLightsCallback(tracks, midiSend).valueChanged(ButtonsMode.SOLO);
            sysexSend.displayText("Solos");
        }
    }

    private Tracks tracks;
    private final MidiSend midiSend;
    private SysexSend sysexSend;

}
