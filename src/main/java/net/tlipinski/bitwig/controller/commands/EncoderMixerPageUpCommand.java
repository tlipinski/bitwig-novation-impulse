package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.EncoderMode;
import net.tlipinski.bitwig.controller.MidiCommand;
import net.tlipinski.bitwig.controller.SysexSend;

import java.util.stream.Stream;

public class EncoderMixerPageUpCommand implements MidiCommand {

    public EncoderMixerPageUpCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;
    }

    @Override
    public Stream<Boolean> conditions(int statusByte, int data1, int data2) {
        return Stream.of(
                controller.getEncoderMode() == EncoderMode.MIXER,
                statusByte == 0xB1,
                data1 == 11
        );
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getEncoderMixerPage().selectPreviousPage(false);
        sysexSend.displayText(controller.getEncoderMixerPage().name());
    }

    private final Controller controller;
    private final SysexSend sysexSend;

}
