package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.Transport;
import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class TransportRecordCommand implements MidiCommand {

    public TransportRecordCommand(Transport transport) {
        this.transport = transport;
    }

    @Override
    public Stream<Boolean> conditions(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 == 32
        );
    }

    @Override
    public void handle(int data1, int data2) {
        if (data2 == 1) {
            transport.record();
        }
    }

    private final Transport transport;
}
