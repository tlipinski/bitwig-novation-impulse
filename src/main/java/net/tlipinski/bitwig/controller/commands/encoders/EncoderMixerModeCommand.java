package net.tlipinski.bitwig.controller.commands.encoders;

import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class EncoderMixerModeCommand implements MidiCommand {

    public EncoderMixerModeCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;

        midiSend.resetEncoderMode();
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB1,
                data1 == 9
        );
    }

    @Override
    public void handle(int data1, int data2) {
        controller.changeEncoderMode(EncoderMode.MIXER);
        sysexSend.displayText("Mixer");
    }

    private Controller controller;
    private final MidiSend midiSend;
    private SysexSend sysexSend;

}
