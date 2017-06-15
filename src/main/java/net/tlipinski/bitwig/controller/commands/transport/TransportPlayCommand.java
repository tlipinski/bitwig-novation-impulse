package net.tlipinski.bitwig.controller.commands.transport;

import com.bitwig.extension.controller.api.Transport;
import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class TransportPlayCommand implements MidiCommand {

    public TransportPlayCommand(Transport transport) {
        this.transport = transport;
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 == 30
        );
    }

    @Override
    public void handle(int data1, int data2) {
        if (data2 == 1) {
            transport.play();
        }
    }

    private final Transport transport;
}
