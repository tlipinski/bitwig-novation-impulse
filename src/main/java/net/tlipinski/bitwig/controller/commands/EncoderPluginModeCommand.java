package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class EncoderPluginModeCommand implements MidiCommand {

    public EncoderPluginModeCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB1,
                data1 == 10
        );
    }

    @Override
    public void handle(int data1, int data2) {
        controller.changeEncoderMode(EncoderMode.PLUGIN);
        sysexSend.displayText("Plugin");
    }

    private Controller controller;
    private final MidiSend midiSend;
    private SysexSend sysexSend;

}
