package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class ClipLauncherCommand implements MidiCommand {

    public ClipLauncherCommand(Controller controller, MidiSend midiSend) {
        this.controller = controller;
        this.midiSend = midiSend;

        for (int i = 0; i < 8; i++) {
            midiSend.send(0xB0, 60 + i, 0);
        }
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 >= 60,
                data1 <= 67
        );
    }

    @Override
    public void handle(int data1, int data2) {

    }

    private Controller controller;
    private final MidiSend midiSend;
}
