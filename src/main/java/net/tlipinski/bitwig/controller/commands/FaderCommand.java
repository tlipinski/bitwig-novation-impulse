package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;
import net.tlipinski.bitwig.controller.MidiSend;
import net.tlipinski.bitwig.controller.SysexSend;

import java.util.stream.Stream;

public class FaderCommand implements MidiCommand {

    public FaderCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 >= 0,
                data1 <= 8
        );
    }

    @Override
    public void handle(int data1, int data2) {
        Track t = this.controller.getTracks().get(data1);
        if (t != null) {
            t.getVolume().set(data2, 128);
            this.sysexSend.displayText(t.name().get());

            // this will display volume value
            this.midiSend.send(0xB0, data1, data2);
        } else {
            this.sysexSend.displayText("<empty>");
        }
    }

    private Controller controller;
    private final MidiSend midiSend;
    private SysexSend sysexSend;

}
