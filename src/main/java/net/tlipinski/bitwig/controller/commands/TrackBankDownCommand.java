package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class TrackBankDownCommand implements MidiCommand {

    public TrackBankDownCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 == 36
        ).allMatch(b -> b);
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getTracks().getTrackBank().scrollChannelsPageDown();
    }

    private final Controller controller;

}
