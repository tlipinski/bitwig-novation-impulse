package net.tlipinski.commands;

import com.bitwig.extension.controller.api.Transport;
import net.tlipinski.MidiCommand;

public class TransportCommand implements MidiCommand {

    public TransportCommand(Transport transport) {
        this.transport = transport;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (27 <= data1 && data1 <= 32);
    }

    @Override
    public void handle(int data1, int data2) {
        if (data2 == 1) {
            switch (data1) {
                case 27: transport.rewind(); break;
                case 28: transport.fastForward(); break;
                case 29: transport.stop(); break;
                case 30: transport.play(); break;
                case 31: transport.isArrangerLoopEnabled().toggle(); break;
                case 32: transport.record(); break;
            }
        }
    }

    private final Transport transport;
}
