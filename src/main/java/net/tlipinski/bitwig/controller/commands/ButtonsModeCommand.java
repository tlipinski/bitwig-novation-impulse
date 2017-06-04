package net.tlipinski.bitwig.controller.commands;

import net.tlipinski.bitwig.controller.*;
import net.tlipinski.bitwig.controller.observers.callbacks.RefreshAllLightsCallback;

public class ButtonsModeCommand implements MidiCommand {

    public ButtonsModeCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;

        midiSend.resetButtonsMode();
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (data1 == 34);
    }

    @Override
    public void handle(int data1, int data2) {
        if (data2 == 1) {
            controller.changeButtonsMode(ButtonsMode.MUTE);
            new RefreshAllLightsCallback(controller, midiSend).valueChanged(ButtonsMode.MUTE);
            sysexSend.displayText("Mutes");
        }
        if (data2 == 0){
            controller.changeButtonsMode(ButtonsMode.SOLO);
            new RefreshAllLightsCallback(controller, midiSend).valueChanged(ButtonsMode.SOLO);
            sysexSend.displayText("Solos");
        }
    }

    private Controller controller;
    private final MidiSend midiSend;
    private SysexSend sysexSend;

}
