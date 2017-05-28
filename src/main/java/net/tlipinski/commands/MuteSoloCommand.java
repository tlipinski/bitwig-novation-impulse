package net.tlipinski.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.*;

public class MuteSoloCommand implements MidiCommand {

    public MuteSoloCommand(Tracks tracks, MidiSend midiSend, SysexSend sysexSend) {
        this.tracks = tracks;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (9 <= data1 && data1 <= 17);
    }

    @Override
    public void handle(int data1, int data2) {
        int buttonIndex = data1 - 9;

        if (tracks.getButtonsMode() == ButtonsMode.MUTE) {
            if (data2 == 1) {
                Track t = tracks.get(buttonIndex);
                if (t != null) {
                    t.getMute().toggle();
                    boolean mute = t.getMute().get();
                    midiSend.light(buttonIndex, !mute);

                    sysexSend.displayText(t.name().get());
                } else {
                    sysexSend.displayText("<empty>");
                }
            }
        }
        if (tracks.getButtonsMode() == ButtonsMode.SOLO) {
            if (data2 == 1) {
                Track t = tracks.get(buttonIndex);
                if (t != null) {
                    t.getSolo().toggle();
                    boolean solo = t.getSolo().get();
                    midiSend.light(buttonIndex, solo);

                    sysexSend.displayText(t.name().get());
                } else {
                    sysexSend.displayText("<empty>");
                }
            }
        }
    }

    private final Tracks tracks;
    private final MidiSend midiSend;
    private final SysexSend sysexSend;

}
