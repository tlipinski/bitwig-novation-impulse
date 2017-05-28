package net.tlipinski.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.MidiCommand;
import net.tlipinski.SysexSend;
import net.tlipinski.Tracks;

public class FaderCommand implements MidiCommand {

    public FaderCommand(Tracks tracks, SysexSend sysexSend) {
        this.tracks = tracks;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (0 <= data1 && data1 <= 8);
    }

    @Override
    public void handle(int data1, int data2) {
        Track t = this.tracks.get(data1);
        if (t != null) {
            t.getVolume().set(data2, 128);
            this.sysexSend.displayText(t.name().get());
        } else {
            this.sysexSend.displayText("<empty>");
        }
    }

    private Tracks tracks;
    private SysexSend sysexSend;

}
