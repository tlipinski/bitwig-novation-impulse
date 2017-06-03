package net.tlipinski.commands;

import net.tlipinski.*;

public class EncoderPluginModeCommand implements MidiCommand {

    public EncoderPluginModeCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB1 && (data1 == 10);
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
