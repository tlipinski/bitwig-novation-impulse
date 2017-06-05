package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class TrackBankUpCommand implements MidiCommand {

    public TrackBankUpCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Stream<Boolean> conditions(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 == 35
        );
    }

    @Override
    public void handle(int data1, int data2) {
        controller.getTracks().getTrackBank().scrollChannelsPageUp();
    }

    private final Controller controller;

}
