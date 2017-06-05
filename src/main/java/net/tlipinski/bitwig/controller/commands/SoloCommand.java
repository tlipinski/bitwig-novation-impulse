package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class SoloCommand implements MidiCommand {

    public SoloCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public Stream<Boolean> conditions(int statusByte, int data1, int data2) {
        return Stream.of(
                controller.getButtonsMode() == ButtonsMode.SOLO,
                statusByte == 0xB0,
                data1 >= 9,
                data1 <= 17
        );
    }

    @Override
    public void handle(int data1, int data2) {
        int buttonIndex = data1 - 9;
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

    private final Controller controller;
    private final MidiSend midiSend;
    private final SysexSend sysexSend;

}
