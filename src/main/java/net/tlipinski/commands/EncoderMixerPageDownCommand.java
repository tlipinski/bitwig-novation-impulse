package net.tlipinski.commands;

import net.tlipinski.Controller;
import net.tlipinski.MidiCommand;
import net.tlipinski.EncoderMode;
import net.tlipinski.SysexSend;

public class EncoderMixerPageDownCommand implements MidiCommand {

    public EncoderMixerPageDownCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (controller.getEncoderMode() == EncoderMode.MIXER) && (statusByte == 0xB1) && (data1 == 12);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getEncoderMixerPage().selectNextPage(false);
        sysexSend.displayText(controller.getEncoderMixerPage().name());
    }

    private final Controller controller;
    private final SysexSend sysexSend;

}
