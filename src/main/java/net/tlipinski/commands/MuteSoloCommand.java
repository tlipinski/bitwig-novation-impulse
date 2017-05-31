package net.tlipinski.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.*;

public class MuteSoloCommand implements MidiCommand {

    public MuteSoloCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
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

        if (controller.getButtonsMode() == ButtonsMode.MUTE) {
            if (data2 == 1) {
                Track t = controller.getTracks().get(buttonIndex);
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
        if (controller.getButtonsMode() == ButtonsMode.SOLO) {
            if (data2 == 1) {
                Track t = controller.getTracks().get(buttonIndex);
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

    private final Controller controller;
    private final MidiSend midiSend;
    private final SysexSend sysexSend;

}
