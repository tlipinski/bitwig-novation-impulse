package net.tlipinski;

public interface MidiCommand {
    boolean triggersFor(int statusByte, int data1, int data2);

    void handle(int data1, int data2);
}
