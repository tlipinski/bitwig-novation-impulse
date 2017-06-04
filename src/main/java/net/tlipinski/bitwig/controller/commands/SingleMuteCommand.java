package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class SingleMuteCommand implements MidiCommand {

    public SingleMuteCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return Stream.of(
                controller.getButtonsMode() == ButtonsMode.MUTE,
                controller.isShiftPressed(),
                statusByte == 0xB0,
                data1 >= 9,
                data1 <= 17
        ).allMatch(b -> b);
    }

    @Override
    public void handle(int data1, int data2) {
        int buttonIndex = data1 - 9;
        if (data2 == 1) {
            Track t = controller.getTracks().get(buttonIndex);
            if (t != null) {
                allLightsOn();

                t.getMute().set(true);
                midiSend.light(buttonIndex, false);
                sysexSend.displayText(t.name().get());
            } else {
                sysexSend.displayText("<empty>");
            }
        }
    }

    private void allLightsOn() {
        for (Track track : controller.getTracks().getAll()) {
            track.getMute().set(false);
        }
    }

    private final Controller controller;
    private final MidiSend midiSend;
    private final SysexSend sysexSend;

}
