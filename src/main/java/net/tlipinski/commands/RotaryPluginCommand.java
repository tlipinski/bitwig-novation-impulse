package net.tlipinski.commands;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import com.bitwig.extension.controller.api.RemoteControl;
import net.tlipinski.*;

public class RotaryPluginCommand implements MidiCommand {

    public RotaryPluginCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;

        CursorRemoteControlsPage cursorRemoteControlsPage = controller.getTracks().getCursorRemoteControlsPage();

        for (int i = 0; i < 8; i++) {
            cursorRemoteControlsPage.getParameter(i).name().markInterested();
            cursorRemoteControlsPage.getParameter(i).markInterested();
        }
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (controller.getRotaryMode() == RotaryMode.PLUGIN) &&
                (statusByte == 0xB1) && (0 <= data1 && data1 <= 7);
    }

    @Override
    public void handle(int data1, int data2) {
        int rotaryIndex = data1;
        RemoteControl parameter = controller.getTracks().getCursorRemoteControlsPage().getParameter(rotaryIndex);

        double mod = (data2 - 64) * 0.01;
        parameter.inc(mod);

        sysexSend.displayText(parameter.name().get());
        int val = (int) (parameter.get() * 100);
        midiSend.send(0xB1, data1, val);
    }

    private final Controller controller;
    private final MidiSend midiSend;
    private final SysexSend sysexSend;

}
