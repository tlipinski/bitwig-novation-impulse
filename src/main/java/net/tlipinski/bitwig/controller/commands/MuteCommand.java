package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class MuteCommand implements MidiCommand {

    public MuteCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                controller.getButtonsMode() == ButtonsMode.MUTE,
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
                t.getMute().toggle();
                boolean mute = t.getMute().get();
                midiSend.light(buttonIndex, !mute);

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
