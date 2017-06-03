package net.tlipinski.commands;

import net.tlipinski.Controller;
import net.tlipinski.MidiCommand;
import net.tlipinski.RotaryMode;
import net.tlipinski.SysexSend;

public class RotaryMixerPageDownCommand implements MidiCommand {

    public RotaryMixerPageDownCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (controller.getRotaryMode() == RotaryMode.MIXER) && (statusByte == 0xB1) && (data1 == 12);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getRotaryMixerPage().selectNextPage(false);
        sysexSend.displayText(controller.getRotaryMixerPage().name());
    }

    private final Controller controller;
    private final SysexSend sysexSend;

}
