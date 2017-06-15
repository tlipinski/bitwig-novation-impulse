package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;
import net.tlipinski.bitwig.controller.MidiSend;
import net.tlipinski.bitwig.controller.SysexSend;

import java.util.stream.Stream;

public class FaderCursorCommand implements MidiCommand {

    public FaderCursorCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;

        controller.getTracks().getCursorTrack().name().markInterested();
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                controller.getModel().isImpulse25(),
                statusByte == 0xB0,
                data1 == 8
        );
    }

    @Override
    public void handle(int data1, int data2) {
        Track t = this.controller.getTracks().getCursorTrack();
        t.getVolume().set(data2, 128);
        this.sysexSend.displayText(t.name().get());

        // this will display volume value
        this.midiSend.send(0xB0, data1, data2);
    }

    private Controller controller;
    private final MidiSend midiSend;
    private SysexSend sysexSend;

}
