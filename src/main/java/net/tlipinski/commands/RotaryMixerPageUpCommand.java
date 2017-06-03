package net.tlipinski.commands;

import net.tlipinski.Controller;
import net.tlipinski.MidiCommand;
import net.tlipinski.RotaryMode;
import net.tlipinski.SysexSend;

public class RotaryMixerPageUpCommand implements MidiCommand {

    public RotaryMixerPageUpCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (controller.getRotaryMode() == RotaryMode.MIXER) && (statusByte == 0xB1) && (data1 == 11);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getRotaryMixerPage().selectPreviousPage(false);
        sysexSend.displayText(controller.getRotaryMixerPage().name());
    }

    private final Controller controller;
    private final SysexSend sysexSend;

}
