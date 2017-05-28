package net.tlipinski.commands;

import net.tlipinski.ButtonsMode;
import net.tlipinski.MidiCommand;
import net.tlipinski.SysexSend;
import net.tlipinski.Tracks;

public class ButtonsModeCommand implements MidiCommand {

    public ButtonsModeCommand(Tracks tracks, SysexSend sysexSend) {
        this.tracks = tracks;
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
            sysexSend.displayText("Mutes");
        } else {
            tracks.changeButtonsMode(ButtonsMode.SOLO);
            sysexSend.displayText("Solos");
        }
    }

    private Tracks tracks;
    private SysexSend sysexSend;

}
